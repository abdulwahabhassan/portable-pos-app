package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class CardTransferTransactionResult(
    val accountNumber: String?,
    val bankName: String?,
    val amount: Double?,
    val reference: String?,
    val message: String?,
    val dateCreated: String?,
    val statusName: String?,
    val sessionId: String?,
)
