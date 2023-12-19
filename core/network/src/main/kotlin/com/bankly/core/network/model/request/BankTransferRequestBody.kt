package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
sealed class BankTransferRequestBody {
    @Serializable
    data class PhoneNumber(
        val amount: String,
        val recipientAccount: String,
        val pin: String,
        val otp: String,
        val securityQuestionId: String,
        val securityQuestionResponse: String,
        val clientRequestId: String,
        val deviceId: String,
        val channel: String,
    ) : BankTransferRequestBody()

    @Serializable
    data class AccountNumber(
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
        val senderName: String,
    ) : BankTransferRequestBody()
}
