package com.bankly.core.network.model.response

import kotlinx.serialization.Serializable
import com.bankly.core.network.retrofit.model.Any

@Serializable
data class TransactionResponse<T> (
    val items: List<T>?,
    val total: Long?,
    val info: Double?,
    val otherInfo: Any?,
    val message: String?
)