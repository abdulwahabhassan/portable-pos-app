package com.bankly.core.model.entity

data class NotificationAlert(
    val transactionReference: String,
    val amount: Double,
    val accountReference: String,
    val paymentDescription: String,
    val senderAccountNumber: String,
    val senderAccountName: String,
    val sessionId: String,
    val phoneNumber: String,
    val userId: String,
    val transactionDate: String,
    val seen: Boolean = false,
    val senderBankName: String,
    val receiverBankName: String,
    val receiverAccountNumber: String,
    val receiverAccountName: String
)
