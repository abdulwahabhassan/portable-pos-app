package com.bankly.core.entity

data class BankNetwork(
    val bankName: String,
    val bankIcon: String,
    val networkPercentage: Double,
    val totalCount: Long,
)
