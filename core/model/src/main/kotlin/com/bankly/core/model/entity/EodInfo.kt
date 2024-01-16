package com.bankly.core.model.entity

data class EodInfo(
    val settled: Double,
    val availableBalance: Double,
    val responseMessage: String,
    val notificationId: Long,
    val responseCode: String,
    val terminalId: String,
    val balance: Double,
    val amountAdded: Double,
)
