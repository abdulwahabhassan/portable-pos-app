package com.bankly.core.data

data class SyncRecentFundingData(
    val sessionId: String,
    val serialNumber: String,
    val location: String? = null,
)
