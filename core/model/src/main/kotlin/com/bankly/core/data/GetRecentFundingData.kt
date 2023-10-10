package com.bankly.core.data

import kotlinx.serialization.Serializable

@Serializable
data class GetRecentFundingData(
    val updateOnFetch: Boolean,
    val serialNumber: String,
    val location: String? = null,
)
