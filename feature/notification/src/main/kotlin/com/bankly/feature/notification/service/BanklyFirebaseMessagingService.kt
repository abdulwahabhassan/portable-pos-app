package com.bankly.feature.notification.service

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bankly.pos.common.Utils
import com.bankly.pos.database.RecentFundDao
import com.bankly.pos.database.entity.RecentFundEntity
import com.bankly.pos.notification.NotificationBuilder
import com.bankly.pos.notification.NotificationMessage
import com.bankly.pos.notification.model.TransactionPayload
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class BanklyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    @Inject
    lateinit var recentFundDao: RecentFundDao

    @Inject
    lateinit var moshi: Moshi


    override fun onCreate() {
        super.onCreate()
        Timber.d("FCM Notification service created")
    }

    override fun onNewToken(token: String) {

        Timber.d("onNewToken -> FCM token: $token")
        //No need to send token to server again here since we are already doing so on every login
        //into the app in the main activity
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Timber.d("on FCM message received called")
        super.onMessageReceived(p0)
        handleMessage(p0)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        Timber.d("handle FCM message called")
        Timber.d("remote FCM message data: ${remoteMessage.data}")
        remoteMessage.data.entries.forEach {
            Timber.d("remote FCM message data entry: ${it}\n")
        }
        val payload = remoteMessage.data[DATA_PAYLOAD_KEY]
        val title = remoteMessage.data[DATA_TITLE_KEY]
        val body = remoteMessage.data[DATA_BODY_KEY]

        Timber.d("FCM Resolved payload: $payload")
        Timber.d("FCM Resolved tile: $title")
        Timber.d("FCM Resolved body: $body")

        val transactionPayload = resolvePayload(payload, moshi)
        Timber.d("FCM Notification Transaction Payload -> $transactionPayload")

        if (transactionPayload?.transactionTypeName == "Wallet Top Up") {
            CoroutineScope(dispatcher).launch {
                transactionPayload.let {
                    recentFundDao.insertRecentFund(
                        RecentFundEntity(
                            transactionReference = it.transactionReference ?: "",
                            amount = it.amount ?: 0.00,
                            accountReference = it.accountReference ?: "",
                            paymentDescription = it.paymentDescription ?: "",
                            senderAccountNumber = it.senderAccountNumber ?: "",
                            senderAccountName = it.senderAccountName ?: "",
                            phoneNumber = it.phoneNumber ?: "",
                            userId = it.userId ?: "",
                            transactionDate = it.transactionDate ?: "",
                            seen = false,
                            senderBankName = it.senderBankName ?: "",
                            receiverBankName = it.receiverBankName ?: "",
                            receiverAccountNumber = it.receiverAccountNumber ?: "",
                            receiverAccountName = it.receiverAccountName ?: "",
                            sessionId = it.sessionID ?: ""
                        )
                    )
                }
            }
            broadcastCreditAlert(transactionPayload)
        } else if (title?.isNotEmpty() == true && body?.isNotEmpty() == true) {
            broadcastNotificationMessage(title, body)
        }

    }

    private fun displayNotificationBanner(title: String, body: String) {
        Utils.playSuccessSound(this)
        NotificationBuilder(context = this, messageBody = body, messageTitle = title).postNotification()
    }

    private fun broadcastCreditAlert(transactionPayload: TransactionPayload) {
        Timber.d("Notification transaction payload -> $transactionPayload")
        Timber.d(
            "Broadcasting alert message, sender -> " +
                    "${transactionPayload.senderAccountName}, " +
                    "amount -> ${transactionPayload.amount}"
        )
        val intent = Intent(PAY_WITH_TRANSFER_CREDIT_ALERT_EVENT)
        intent.putExtra(DATA_PAYLOAD_KEY, transactionPayload)
        Utils.playSuccessSound(this)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun broadcastNotificationMessage(title: String, body: String) {
        Timber.d("Broadcasting notification message: title -> $title, body -> $body")
        val intent = Intent(NOTIFICATION_MESSAGE_EVENT)
        intent.putExtra(DATA_MESSAGE_KEY, NotificationMessage(title, body, LocalDateTime.now().toString()))
        Utils.playSuccessSound(this)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    companion object {
        const val FIREBASE_MESSAGING_EVENT = "com.google.firebase.MESSAGING_EVENT"
        const val PAY_WITH_TRANSFER_CREDIT_ALERT_EVENT =
            "com.bankly.paywithtransfer.CREDIT_ALERT_EVENT"
        const val NOTIFICATION_MESSAGE_EVENT =
            "com.bankly.notification.MESSAGE_EVENT"
        const val DATA_PAYLOAD_KEY = "payload"
        const val DATA_MESSAGE_KEY = "message"
        const val DATA_TITLE_KEY = "title"
        const val DATA_BODY_KEY = "body"

        fun resolvePayload(payload: String?, moshi: Moshi): TransactionPayload? {
            val transactionPayloadType = object : TypeToken<TransactionPayload>() {}.type
            val transactionPayloadAdapter: JsonAdapter<TransactionPayload>? =
                moshi.adapter(transactionPayloadType)
            return payload?.let { transactionPayloadAdapter?.fromJson(it) }
        }
    }

}