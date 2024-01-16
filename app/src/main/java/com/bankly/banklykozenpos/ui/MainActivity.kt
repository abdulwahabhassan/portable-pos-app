package com.bankly.banklykozenpos.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bankly.banklykozenpos.R
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.notification.model.TransactionPayload
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val transferAlertBroadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {

            val transactionPayload = intent.getParcelableExtra<TransactionPayload>(
                BanklyFirebaseMessagingService.DATA_PAYLOAD_KEY
            )

            Log.d("debug fcm",
                "Transfer alert Broadcast Received: " +
                        "name -> ${transactionPayload?.senderAccountName} " +
                        "amount -> ${transactionPayload?.amount.toString()}"
            )

            transactionPayload?.let { showCreditAlertDialog(it) }
                ?: Log.d("Recent fund from Notification payload is null")
        }
    }
    private val notificationMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val notificationMessage = intent.getParcelableExtra<NotificationMessage>(
                BanklyFirebaseMessagingService.DATA_MESSAGE_KEY
            )
            Log.d(
                "debug fcm",
                "Notification message Broadcast Received: " +
                        "title -> ${notificationMessage?.title} " +
                        "body -> ${notificationMessage?.message}" +
                        "date -> ${notificationMessage?.dateTime}"
            )

            notificationMessage?.let { showNotificationMessage(it) }
                ?: Log.d("debug fcm", "Notification message title and body is empty")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            BanklyTheme {
                BanklyApp(
                    onCloseApp = {
                        finish()
                    },
                    activity = this,
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(transferAlertBroadCastReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationMessageReceiver)
    }

    private val fCMRegistrationToken: Unit
        get() {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(this) { task: Task<String?> ->
                if (task.isSuccessful) {
                    Log.d("debug fcm", "Fetching FCM registration token success")

                    val deviceToken = task.result

                    val msg = getString(R.string.msg_token_fmt, deviceToken)
                    Log.d("debug fcm", "Retrieved FCM TokenApiService $msg")

                    //Send FCM token to server
                    sendFcmTokenToServer(deviceToken)

                    //When the device token is retrieved, Firebase can now connect with the device
                } else {
                    Log.d("debug fcm", "Fetching FCM registration token failed")
                    Log.d("debug fcm", "FCM TokenApiService Exception " + task.exception!!.localizedMessage)
                }
            }
        }

    private fun sendFcmTokenToServer(deviceToken: String?) {
        notificationViewModel!!.addDeviceToFireBase(deviceToken!!, Sdk.getSerialNo(this))
    }
}
