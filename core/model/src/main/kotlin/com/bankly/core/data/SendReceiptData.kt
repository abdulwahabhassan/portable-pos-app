package com.bankly.core.data

import kotlinx.serialization.Serializable

@Serializable
data class SendReceiptData(
    val sessionId: String,
    val beneficiary: String,
    val routeType: Long,
)
