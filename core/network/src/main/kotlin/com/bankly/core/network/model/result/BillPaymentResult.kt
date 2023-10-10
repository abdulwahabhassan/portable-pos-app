package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class BillPaymentResult (
    val id: Long,
    val reference: String?,
    val narration: String?,
    val description: String?,
    val amount: Double?,
    val paymentType: String?,
    val paidFor: String?,
    val paidForName: String?,
    val paidByAccountId: Long?,
    val paidByAccountNo: String?,
    val paidByAccountName: String?,
    val paidOn: String?,
    val polled: Boolean?,
    val responseStatus: Long?,
    val transactionType: String?,
    val billName: String?,
    val billItemName: String?,
    val receiver: String?,
    val commission: Double?,
    val billToken: String?,
    val isTokenType: Boolean?
)
