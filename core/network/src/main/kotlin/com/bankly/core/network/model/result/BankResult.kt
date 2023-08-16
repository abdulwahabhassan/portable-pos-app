package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class BankResult (
    val name: String,
    val id: Long,
    val categoryId: Int? = null,
    val categoryName: String? = null
)