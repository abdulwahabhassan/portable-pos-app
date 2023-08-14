package com.bankly.core.network.model.request

data class ExternalTransferRequestBody(
    val accountName: String,
    val accountNumber: String,
    val bankId: String,
    val bankName: String,
    val narration: String,
    val phoneNumber: String,
    val amountToSend: String,
    val otp: String,
    val channel: String,
    val clientRequestId: String,
    val securityQuestionId: String,
    val securityQuestionResponse: String,
    val deviceId: String,
    val isWeb: String,
    val senderName: String
)