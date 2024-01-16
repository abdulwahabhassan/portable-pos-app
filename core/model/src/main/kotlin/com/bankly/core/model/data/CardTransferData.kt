package com.bankly.core.model.data

data class CardTransferData(
    val accountName: String,
    val inquiryReference: String,
    val accountNumber: String,
    val amount: Double,
    val narration: String,
    val channel: String,
    val sendersPhoneNumber: String,
    val clientRequestId: String,
    val responseCode: String,
    val responseMessage: String,
    val bankName: String
)
