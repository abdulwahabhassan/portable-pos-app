package com.bankly.core.data

data class InternalTransferData(
    val amount: String,
    val recipientAccount: String,
    val pin: String,
    val otp: String,
    val securityQuestionId: String,
    val securityQuestionResponse: String,
    val clientRequestId: String,
    val deviceId: String
)
