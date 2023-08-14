package com.bankly.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NameEnquiryResult (
    val accountName: String?,
    val accountNumber: String?,
    val bankCode: String?,
    val bankName: String?
)