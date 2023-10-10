package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SyncRecentFundingRequestBody(
    val sessionId: String,
    val serialNumber: String,
    val location: String? = null,
)
