package com.bankly.core.data

data class SendReceiptData(
    val sessionId: String,
    val beneficiary: String,
    val routeType: Long,
)
