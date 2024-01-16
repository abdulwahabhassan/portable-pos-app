package com.bankly.core.model.data

data class SendReceiptData(
    val sessionId: String,
    val beneficiary: String,
    val routeType: Long,
)
