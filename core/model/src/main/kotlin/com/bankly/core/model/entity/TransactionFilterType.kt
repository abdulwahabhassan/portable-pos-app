package com.bankly.core.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class TransactionFilterType(
    val name: String,
    val id: Long,
    val isSelected: Boolean,
)
