package com.bankly.core.model

data class ExternalTransfer(
    val accountName: String,
    val accountNumber: String,
    val bankId: String,
    val bankName: String,
    val narration: String,
    val phoneNumber: String = "",
    val amountToSend: String,
    val otp: String,
    val channel: String = "4",
    val clientRequestId: String = "",
    val securityQuestionId: String,
    val securityQuestionResponse: String,
    val deviceId: String,
    val isWeb: String = "false",
    val senderName: String
)
