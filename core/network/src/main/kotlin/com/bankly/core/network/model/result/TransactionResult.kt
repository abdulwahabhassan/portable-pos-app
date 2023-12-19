package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResult(
    val creditAccountNo: String?,
    val debitAccountNo: String?,
    val transactionBy: String?,
    val channelName: String?,
    val statusName: String?,
    val userType: Long?,
    val userId: Long?,
    val initiator: String?,
    val archived: Boolean?,
    val product: String?,
    val hasProduct: Boolean?,
    val senderName: String?,
    val receiverName: String?,
    val balanceBeforeTransaction: Double?,
    val leg: Long?,
    val id: Long?,
    val reference: String?,
    val transactionType: Long?,
    val transactionTypeName: String?,
    val description: String?,
    val narration: String?,
    val amount: Double?,
    val creditAccountNumber: String?,
    val deditAccountNumber: String?,
    val parentReference: String?,
    val transactionDate: String?,
    val credit: Double?,
    val debit: Double?,
    val balanceAfterTransaction: Double?,
    val sender: String?,
    val receiver: String?,
    val channel: Long?,
    val status: Long?,
    val charges: Double?,
    val aggregatorCommission: Double?,
    val hasCharges: Boolean?,
    val agentCommission: Double?,
    val debitAccountNumber: String?,
    val initiatedBy: String?,
    val stateId: Long?,
    val lgaId: Long?,
    val regionId: String?,
    val aggregatorId: Long?,
    val isCredit: Boolean?,
    val isDebit: Boolean?,
)
