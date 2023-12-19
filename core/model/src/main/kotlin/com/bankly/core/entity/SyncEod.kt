package com.bankly.core.entity

data class SyncEod(
    val responseMessage: String,
    val notificationId: Long,
    val responseCode: String,
    val terminalId: String,
    val balance: Double,
    val amountAdded: Double,
)
