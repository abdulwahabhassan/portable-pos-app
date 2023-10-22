package com.bankly.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class TransactionFilterType(
    val name: String,
    val id: Long,
    val isSelected: Boolean
)
