package com.bankly.feature.notification.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PushNotificationMessage(
    val title: String,
    val message: String,
    val dateTime: String,
    val seen: Boolean
): Parcelable