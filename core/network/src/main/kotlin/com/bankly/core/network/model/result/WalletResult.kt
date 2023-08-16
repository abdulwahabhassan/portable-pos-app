package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable


@Serializable
data class WalletResult (
    val id: Long,
    val accountNumber: String,
    val code: String,
    val name: String,
    val accountTypeId: Long,
    val ownerId: Long,
    val currentBalance: Double,
    val availableBalance: Double,
    val externalPaymentBalance: Double,
    val currencyId: Long,
    val userId: String,
    val active: Boolean,
    val default: Boolean,
    val status: String,
    val createdBy: String,
    val createdOn: String,
    val lastUpdatedBy: String,
    val lastUpdatedOn: String,
    val providusAccountNumber: String,
    val hasPnd: Boolean,
    val accountTypeName: String,
    val hasLimit: Boolean,
    val fundingSource: Long,
    val canPerformCardWithdrawal: Boolean,
    val fundingSourceName: String,
    val fundingAccountNumber: String,
    val hasFundingAccountNumber: Boolean,
    val bankName: String,
    val isStaffAccount: Boolean,
    val hasGeneratedNewNuban: Boolean,
    val ownerType: Long,
    val nuban: String
)