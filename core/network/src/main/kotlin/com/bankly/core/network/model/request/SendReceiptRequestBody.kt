package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SendReceiptRequestBody(
    val sessionId: String,
    val beneficiary: String,
    val routeType: Long,
)
