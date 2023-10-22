package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class TransactionFilterTypeResult(
    val name: String,
    val id: Long,
)
