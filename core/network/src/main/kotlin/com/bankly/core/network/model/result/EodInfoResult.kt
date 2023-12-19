package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class EodInfoResult(
    val settled: Double?,
    val availableBalance: Double?,
    val responseMessage: String?,
    val notificationId: Long?,
    val responseCode: String?,
    val terminalId: String?,
    val balance: Double?,
    val amountAdded: Double?,
)
