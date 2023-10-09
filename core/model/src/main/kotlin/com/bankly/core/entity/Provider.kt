package com.bankly.core.entity

data class Provider (
    val id: Long,
    val categoryId: Long,
    val code: String,
    val name: String,
    val description: String,
    val amount: Double,
    val hasItems: Boolean,
    val active: Boolean,
    val billType: Long,
    val provider: Long,
    val transactionChargeCommissionType: Long,
    val transactionCharge: Double,
    val agentCommissionType: Long,
    val agentCommissionValue: Double,
    val aggregatorCommissionType: Long,
    val aggregatorCommissionValue: Double,
    val providerPercentage: Double,
    val billImageUrl: String,
    val providerAmount: Double,
    val providerId: Long,
    val dateCreated: String,
    val minimumAmount: Double
)
