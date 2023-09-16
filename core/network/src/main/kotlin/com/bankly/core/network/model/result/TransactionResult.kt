package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResult(
    val phoneNumber: String?,
    val amount: Double?,
    val reference: String?,
    val accountNumber: String?,
    val sourceWallet: Long?,
    val paymentGateway: Long?,
    val message: String?,
    val bankName: String?,
    val beneficiaryAccount: String?,
    val accountName: String?,
    val sourceWalletName: String?,
    val dateCreated: String?,
    val statusName: String?,
    val sessionId: String?,
)
