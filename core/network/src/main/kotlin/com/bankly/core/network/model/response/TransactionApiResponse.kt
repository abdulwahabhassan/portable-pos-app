package com.bankly.core.network.model.response

import com.bankly.core.network.retrofit.model.Any
import kotlinx.serialization.Serializable

@Serializable
data class TransactionApiResponse<T> (
    val items: List<T>?,
    val total: Long?,
    val info: Double?,
    val otherInfo: Any?,
    val message: String?,
)
