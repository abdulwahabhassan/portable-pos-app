package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GetRecentFundingRequestBody(
    val updateOnFetch: Boolean,
    val serialNumber: String,
    val location: String? = null,
)
