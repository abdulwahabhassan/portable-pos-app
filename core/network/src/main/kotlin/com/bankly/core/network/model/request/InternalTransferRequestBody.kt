package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumberTransferRequestBody(
    val amount: String,
    val recipientAccount: String,
    val pin: String,
    val otp: String,
    val securityQuestionId: String,
    val securityQuestionResponse: String,
    val clientRequestId: String,
    val deviceId: String,
    val channel: String,
)
