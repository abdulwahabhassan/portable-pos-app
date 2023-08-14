package com.bankly.feature.cardtransfer.model

data class TransactionDetails(
    val accountNumber: String,
    val accountName: String,
    val amount: Double,
    val bankName: String,
    val bankId: String,
    val narration: String,
    val senderPhoneNumber: String
)