package com.bankly.banklykozenpos.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bankly.banklykozenpos.R
import com.bankly.core.common.util.parcelable
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.model.entity.NotificationMessage
import com.bankly.feature.notification.model.PushNotificationMessage
import com.bankly.feature.notification.model.TransactionPayload
import com.bankly.feature.notification.service.BanklyFirebaseMessagingService
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var transactionPayload: TransactionPayload? = null
    private var notificationMessage: NotificationMessage? = null
    private val viewModel by viewModels<MainActivityViewModel>()

    @Inject
    lateinit var json: Json

    private val transferAlertBroadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {

            val transactionPayload = intent.parcelable<TransactionPayload>(
                BanklyFirebaseMessagingService.DATA_PAYLOAD_KEY
            )

            Log.d(
                "debug fcm",
                "Transfer alert Broadcast Received: " +
                        "name -> ${transactionPayload?.senderAccountName} " +
                        "amount -> ${transactionPayload?.amount.toString()}"
            )

            transactionPayload?.let { handleTransactionPayload(it) }
                ?: Log.d("debug fcm", "Recent fund from Notification payload is null")
        }
    }
    private val notificationMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val notificationMessage = intent.getParcelableExtra<PushNotificationMessage>(
                BanklyFirebaseMessagingService.DATA_MESSAGE_KEY
            )
            Log.d(
                "debug fcm",
                "Notification message Broadcast Received: " +
                        "title -> ${notificationMessage?.title} " +
                        "body -> ${notificationMessage?.message}" +
                        "date -> ${notificationMessage?.dateTime}"
            )

            notificationMessage?.let {
                handleNotificationMessage(
                    NotificationMessage(
                        title = it.title,
                        message = it.message,
                        dateTime = it.dateTime,
                        seen = it.seen
                    )
                )
            }
                ?: Log.d("debug fcm", "Notification message title and body is empty")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            BanklyTheme {
                val mainActivityState by viewModel.uiState.collectAsStateWithLifecycle()
                BanklyApp(
                    appState = rememberBanklyAppState(mainActivityState = mainActivityState),
                    onCloseApp = {
                        finish()
                    },
                    activity = this,
                    onClosNotificationMessageDialog = { notificationMessage: NotificationMessage ->
                        viewModel.sendEvent(
                            MainActivityEvent.OnDismissNotificationMessageDialog(
                                notificationMessage
                            )
                        )
                    },
                    onCloseTransactionAlertDialog = { transactionPayload: TransactionPayload ->
                        viewModel.sendEvent(
                            MainActivityEvent.OnDismissTransactionAlertDialog(transactionPayload)
                        )
                    },
                )

            }
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(
            transferAlertBroadCastReceiver,
            IntentFilter(BanklyFirebaseMessagingService.PAY_WITH_TRANSFER_CREDIT_ALERT_EVENT)
        )
        LocalBroadcastManager.getInstance(this).registerReceiver(
            notificationMessageReceiver,
            IntentFilter(BanklyFirebaseMessagingService.NOTIFICATION_MESSAGE_EVENT)
        )
        fCMRegistrationToken
        resolveIntent(intent)
    }

    private fun resolveIntent(intent: Intent?) {
        if (intent != null) {
            Log.d("", "Resolve intent called Intent -> $intent")
            if (intent.extras != null) {
                //PROCESS NOTIFICATION DATA
                val payload =
                    intent.extras!!.getString(BanklyFirebaseMessagingService.DATA_PAYLOAD_KEY)
                val title =
                    intent.extras!!.getString(BanklyFirebaseMessagingService.DATA_TITLE_KEY)
                val body =
                    intent.extras!!.getString(BanklyFirebaseMessagingService.DATA_BODY_KEY)
                val preTransactionPayload =
                    BanklyFirebaseMessagingService.resolvePayload(payload, json)
                if (preTransactionPayload?.transactionReference != null && preTransactionPayload.amount != null) {
                    transactionPayload = preTransactionPayload
                } else if (preTransactionPayload?.title != null && preTransactionPayload.body != null) {
                    notificationMessage = NotificationMessage(
                        preTransactionPayload.title!!,
                        preTransactionPayload.body!!,
                        LocalDateTime.now().toString(),
                        seen = false
                    )
                } else if (title != null && body != null) {
                    notificationMessage =
                        NotificationMessage(
                            title = title,
                            message = body,
                            dateTime = LocalDateTime.now().toString(),
                            seen = false
                        )
                }
                Log.d(
                    "",
                    "Resolve intent called -> "
                            + "notification transaction payload: " + transactionPayload
                )
                Log.d(
                    "",
                    "Resolve intent called -> "
                            + "notification message: " + notificationMessage
                )

            }
        }
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("", "New Intent: \$intent")
        resolveIntent(intent)
    }

    private fun handleTransactionPayload(transactionPayload: TransactionPayload) {
        Log.d("", "Main activity received notification transaction payload->$transactionPayload")
        viewModel.sendEvent(
            MainActivityEvent.OnReceiveTransactionPayload(
                transactionPayload = transactionPayload
            )
        )
    }

    private fun handleNotificationMessage(notificationMessage: NotificationMessage) {
        Log.d(
            "", "Main activity received notification message-> " +
                    "title: ${notificationMessage.title} " +
                    "body: ${notificationMessage.message} " +
                    "date: ${notificationMessage.dateTime}"
        )
        viewModel.sendEvent(
            MainActivityEvent.OnReceiveNotificationMessage(
                notificationMessage = notificationMessage
            )
        )
    }

    private val fCMRegistrationToken: Unit
        get() {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(this) { task: Task<String?> ->
                if (task.isSuccessful) {
                    Log.d("debug fcm", "Fetching FCM registration token success")

                    val deviceToken = task.result

                    val msg = getString(R.string.msg_token_fmt, deviceToken)
                    Log.d("debug fcm", "Retrieved FCM TokenApiService $msg")

                    sendFcmTokenToServer(deviceToken)

                } else {
                    Log.d("debug fcm", "Fetching FCM registration token failed")
                    Log.d(
                        "debug fcm",
                        "FCM TokenApiService Exception " + task.exception!!.localizedMessage
                    )
                }
            }
        }

    private fun sendFcmTokenToServer(deviceToken: String?) {
        viewModel.sendEvent(
            MainActivityEvent.AddDeviceToFirebase(
                deviceId = deviceToken!!,
                userId = Tools.serialNumber
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(transferAlertBroadCastReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationMessageReceiver)
    }

}
