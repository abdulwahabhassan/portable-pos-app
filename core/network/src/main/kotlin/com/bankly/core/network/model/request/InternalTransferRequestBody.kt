package com.bankly.core.network.model.request

data class InternalTransferRequestBody(
    val amount: String,
    val recipientAccount: String,
    val pin: String,
    val otp: String,
    val securityQuestionId: String,
    val securityQuestionResponse: String,
    val clientRequestId: String,
    val deviceId: String
)