package com.bankly.core.model.entity

data class CardTransferAccountInquiry(
    val accountName: String,
    val inquiryReference: String,
    val balance: Double,
    val reference: String,
    val accountNumber: String,
    val bankCode: String,
    val bankId: Long,
    val bankName: String,
)
