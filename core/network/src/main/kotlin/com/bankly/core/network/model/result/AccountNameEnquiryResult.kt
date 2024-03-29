package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class AccountNameEnquiryResult(
    val accountName: String?,
    val accountNumber: String?,
    val bankCode: String?,
    val bankName: String?,
)
