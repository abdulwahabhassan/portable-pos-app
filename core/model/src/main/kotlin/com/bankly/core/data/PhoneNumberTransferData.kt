package com.bankly.core.data

import java.time.LocalDateTime
import java.time.ZoneOffset

data class PhoneNumberTransferData(
    val amount: String,
    val recipientAccount: String,
    val pin: String = "",
    val otp: String,
    val securityQuestionId: String = "",
    val securityQuestionResponse: String = "",
    val clientRequestId: String = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString(),
    val deviceId: String = "",
    val channel: String = "4",
)
