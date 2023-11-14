package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable


@Serializable
data class CardTransferRequestBody(
    val accountName: String,
    val inquiryReference: String,
    val accountNumber: String,
    val amount: Double,
    val narration: String,
    val channel: String,
    val sendersPhoneNumber: String,
    val clientRequestId: String,
    val responseCode: String,
    val responseMessage: String
)
