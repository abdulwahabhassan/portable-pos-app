package com.bankly.core.model.data

data class SyncRecentFundingData(
    val sessionId: String,
    val serialNumber: String,
    val location: String? = null,
)
