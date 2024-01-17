package com.bankly.core.model.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class TransactionFilter(
    val transactionReference: String = "",
    val accountName: String = "",
    val cashFlows: List<com.bankly.core.model.entity.CashFlow> = listOf(com.bankly.core.model.entity.CashFlow.Debit(false), com.bankly.core.model.entity.CashFlow.Credit(false)),
    val transactionTypes: List<TransactionFilterType> = emptyList(),
    val dateFrom: LocalDate? = null,
    val dateTo: LocalDate? = null,
) 