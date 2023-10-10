package com.bankly.core.data

import java.time.LocalDateTime
import java.time.ZoneOffset

sealed class BankTransferData {
    data class AccountNumber(
        val accountName: String,
        val accountNumber: String,
        val bankId: String,
        val bankName: String,
        val narration: String,
        val phoneNumber: String = "",
        val amountToSend: String,
        val otp: String,
        val channel: String = "4",
        val clientRequestId: String,
        val securityQuestionId: String = "",
        val securityQuestionResponse: String = "",
        val deviceId: String = "",
        val isWeb: String = "false",
        val senderName: String = "",
    ) : BankTransferData()

    data class PhoneNumber(
        val amount: String,
        val recipientAccount: String,
        val pin: String = "",
        val otp: String,
        val securityQuestionId: String = "",
        val securityQuestionResponse: String = "",
        val clientRequestId: String,
        val deviceId: String = "",
        val channel: String = "4",
    ): BankTransferData()
}
