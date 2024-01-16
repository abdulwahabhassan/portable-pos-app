package com.bankly.core.model.entity

import kotlinx.serialization.Serializable

@Serializable
sealed class CashFlow(val title: String, val isSelected: Boolean) {
    @Serializable
    data class Credit(val state: Boolean) : com.bankly.core.model.entity.CashFlow(title = "Credit", isSelected = state)

    @Serializable
    data class Debit(val state: Boolean) : com.bankly.core.model.entity.CashFlow(title = "Debit", isSelected = state)
}
