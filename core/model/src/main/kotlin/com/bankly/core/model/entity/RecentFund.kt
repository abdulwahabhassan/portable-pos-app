package com.bankly.core.model.entity

import com.bankly.core.model.sealed.TransactionReceipt

data class RecentFund(
    val transactionReference: String,
    val amount: Double,
    val accountReference: String,
    val paymentDescription: String,
    val transactionHash: String,
    val senderAccountNumber: String,
    val senderAccountName: String,
    val sessionId: String,
    val phoneNumber: String,
    val userId: String,
    val transactionDate: String,
    val seen: Boolean,
    val senderBankName: String,
    val receiverBankName: String,
    val receiverAccountNumber: String,
    val receiverAccountName: String,
) {
    fun toTransactionReceipt(): TransactionReceipt.PayWithTransfer {
        return TransactionReceipt.PayWithTransfer(
            senderAccountName = senderAccountName,
            senderAccountNumber = senderAccountNumber,
            senderBankName = senderBankName,
            amount = amount,
            reference = transactionReference,
            receiverAccountNumber = receiverAccountNumber,
            message = paymentDescription,
            receiverName = receiverAccountName,
            receiverBankName = receiverBankName,
            dateCreated = transactionDate,
            statusName = "Successful",
            sessionId = sessionId,
        )
    }
}
