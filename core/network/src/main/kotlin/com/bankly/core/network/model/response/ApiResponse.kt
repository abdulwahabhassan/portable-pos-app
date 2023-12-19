package com.bankly.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val hasResult: Boolean? = null,
    val result: T? = null,
    val successful: Boolean? = null,
    val resultType: Int? = null,
    val message: String? = null,
    val validationMessages: List<String>? = null,
    val responseCode: String? = null,
)
