package com.bankly.core.data

import kotlinx.serialization.Serializable

typealias EodTransactionListData = List<EodTransactionData>

@Serializable
data class EodTransactionData(
    val serialNumber: String,
    val transactionReference: String,
    val paymentDate: String,
    val responseCode: String,
    val stan: String,
    val responseMessage: String,
    val amount: String,
    val maskedPan: String,
    val terminalId: String,
    val rrn: String,
    val transType: String,
)
