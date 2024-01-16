package com.bankly.core.model.entity


data class NotificationMessage(
    val title: String,
    val message: String,
    val dateTime: String,
    val seen: Boolean
)