package com.bankly.core.entity

import kotlinx.serialization.Serializable

@Serializable
sealed class CashFlow(val title: String, val isSelected: Boolean) {
    @Serializable
    data class Credit(val state: Boolean) : CashFlow(title = "Credit", isSelected = state)

    @Serializable
    data class Debit(val state: Boolean) : CashFlow(title = "Debit", isSelected = state)
}
