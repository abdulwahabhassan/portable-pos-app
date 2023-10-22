package com.bankly.core.data

data class GetRecentFundingData(
    val updateOnFetch: Boolean,
    val serialNumber: String,
    val location: String? = null,
)
