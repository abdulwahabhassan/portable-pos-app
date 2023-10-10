package com.bankly.core.data

import kotlinx.serialization.Serializable

@Serializable
data class SyncRecentFundingData(
    val sessionId: String,
    val serialNumber: String,
    val location: String? = null,
)
