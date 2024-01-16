package com.bankly.core.model.data

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
    ) : com.bankly.core.model.data.BankTransferData()

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
    ) : com.bankly.core.model.data.BankTransferData()
}
