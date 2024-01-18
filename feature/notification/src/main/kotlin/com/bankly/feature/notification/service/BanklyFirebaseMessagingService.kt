package com.bankly.feature.notification.service

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bankly.core.common.util.playSuccessSound
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.domain.usecase.AddDeviceToFirebaseUseCase
import com.bankly.core.domain.usecase.GetEodTransactionsUseCase
import com.bankly.core.domain.usecase.InsertRecentFundUseCase
import com.bankly.core.domain.usecase.SaveToEodUseCase
import com.bankly.core.model.data.AddDeviceTokenData
import com.bankly.core.model.entity.NotificationMessage
import com.bankly.core.model.entity.RecentFund
import com.bankly.feature.notification.builder.NotificationBuilder
import com.bankly.feature.notification.model.PushNotificationMessage
import com.bankly.feature.notification.model.TransactionPayload
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@AndroidEntryPoint
class BanklyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    @IODispatcher
    lateinit var dispatcher: CoroutineDispatcher

    @Inject
    lateinit var addDeviceToFirebaseUseCase: AddDeviceToFirebaseUseCase

    @Inject
    lateinit var json: Json


    override fun onCreate() {
        super.onCreate()
        Log.d("debug fcm", "FCM Notification service created")
    }

    override fun onNewToken(token: String) {
        Log.d("debug fcm", "onNewToken -> FCM token: $token")
        CoroutineScope(dispatcher).launch {
            addDeviceToFirebaseUseCase.invoke(token, AddDeviceTokenData(token, Tools.serialNumber))
        }
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d("debug fcm", "on FCM message received called")
        super.onMessageReceived(p0)
        handleMessage(p0)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        Log.d("debug fcm", "handle FCM message called")
        Log.d("debug fcm", "remote FCM message data: ${remoteMessage.data}")
        remoteMessage.data.entries.forEach {
            Log.d("debug fcm", "remote FCM message data entry: ${it}\n")
        }
        val payload = remoteMessage.data[DATA_PAYLOAD_KEY]
        val title = remoteMessage.data[DATA_TITLE_KEY]
        val body = remoteMessage.data[DATA_BODY_KEY]

        Log.d("debug fcm", "FCM Resolved payload: $payload")
        Log.d("debug fcm", "FCM Resolved tile: $title")
        Log.d("debug fcm", "FCM Resolved body: $body")

        val transactionPayload = resolvePayload(payload = payload, json = json)
        Log.d("debug fcm", "FCM Notification Transaction Payload -> $transactionPayload")

        if (transactionPayload?.transactionTypeName == "Wallet Top Up") {
            broadcastCreditAlert(transactionPayload)
        } else if (title?.isNotEmpty() == true && body?.isNotEmpty() == true) {
            broadcastNotificationMessage(title, body)
        }

    }

    private fun displayNotificationBanner(title: String, body: String) {
        playSuccessSound(this)
        NotificationBuilder(
            context = this,
            messageBody = body,
            messageTitle = title
        ).postNotification()
    }

    private fun broadcastCreditAlert(transactionPayload: TransactionPayload) {
        Log.d("debug fcm", "Notification transaction payload -> $transactionPayload")
        Log.d(
            "debug fcm",
            "Broadcasting alert message, sender -> " +
                    "${transactionPayload.senderAccountName}, " +
                    "amount -> ${transactionPayload.amount}"
        )
        val intent = Intent(PAY_WITH_TRANSFER_CREDIT_ALERT_EVENT)
        intent.putExtra(DATA_PAYLOAD_KEY, transactionPayload)
        playSuccessSound(this)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun broadcastNotificationMessage(title: String, body: String) {
        Log.d(
            "debug notification message",
            "Broadcasting notification message: title -> $title, body -> $body"
        )
        val intent = Intent(NOTIFICATION_MESSAGE_EVENT)
        intent.putExtra(
            DATA_MESSAGE_KEY,
            PushNotificationMessage(title, body, LocalDateTime.now().toString(), seen = false)
        )
        playSuccessSound(this)
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

        fun resolvePayload(payload: String?, json: Json): TransactionPayload? {
            return payload?.let { json.decodeFromString(it) }
        }
    }

}