package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class RecentFundResult(
    val transactionReference: String?,
    val amount: Double?,
    val accountReference: String?,
    val paymentDescription: String?,
    val transactionHash: String?,
    val senderAccountNumber: String?,
    val senderAccountName: String?,
    val sessionId: String?,
    val phoneNumber: String?,
    val userId: String?,
    val transactionDate: String?,
    val senderBankName: String?,
    val receiverBankName: String?,
    val receiverAccountNumber: String?,
    val receiverAccountName: String?
)
