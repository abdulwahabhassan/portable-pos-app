package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable


typealias SyncEodTransactions = List<SyncEodTransactionData>

@Serializable
data class SyncEodRequestBody(
    val transactions: SyncEodTransactions
)

@Serializable
data class SyncEodTransactionData(
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
    val transType: String
)

