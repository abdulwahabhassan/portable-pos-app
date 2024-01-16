package com.bankly.feature.notification.builder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bankly.feature.notification.R
import com.bankly.feature.notification.service.BanklyFirebaseMessagingService
import java.time.LocalDateTime

class NotificationBuilder(
    private val context: Context,
    private val messageBody: String,
    private val messageTitle: String,
    private val time: String = LocalDateTime.now().toString()
) {

    private val intent = Intent(
        BanklyFirebaseMessagingService.FIREBASE_MESSAGING_EVENT,
        Uri.EMPTY,
        context,
        context::class.java
    ).apply {
        putExtra(DATA_PAYLOAD_TITLE_KEY, messageTitle)
        putExtra(DATA_PAYLOAD_BODY_KEY, messageBody)
        putExtra(DATA_PAYLOAD_TIME_KEY, time)
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }

    private val showMessageBodyPendingIntent: PendingIntent = PendingIntent
        .getActivity(context, NOTIFICATION_ID, intent, FLAG_UPDATE_CURRENT)

    private fun createNotificationBuilder() = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(com.bankly.core.designsystem.R.drawable.ic_bank_icon)
        .setContentTitle(messageTitle)
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(showMessageBodyPendingIntent)
        .setAutoCancel(true)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH

            //initialize a notification channel
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //create channel
            notificationManager.createNotificationChannel(channel)
        }

    }

    fun postNotification() {
        createNotificationChannel()

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, createNotificationBuilder().build())
        }
    }

    companion object {
        const val CHANNEL_ID = "PUSH_NOTIFICATION_ID"
        const val NOTIFICATION_ID = 0
        const val DATA_PAYLOAD_TIME_KEY = "time"
        const val DATA_PAYLOAD_BODY_KEY = "body"
        const val DATA_PAYLOAD_TITLE_KEY = "title"
    }

}