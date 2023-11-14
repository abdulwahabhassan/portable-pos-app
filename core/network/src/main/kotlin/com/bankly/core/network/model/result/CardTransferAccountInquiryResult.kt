package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class CardTransferAccountInquiryResult(
    val accountName: String?,
    val inquiryReference: String?,
    val balance: Double?,
    val reference: String?,
    val accountNumber: String?,
    val bankCode: String?,
    val bankId: Long?,
    val bankName: String?,
)