package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class CableTvNameEnquiryResult(
    val cardNumber: String,
    val customerName: String,
    val packageName: String,
)
