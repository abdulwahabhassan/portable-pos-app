package com.bankly.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * Wrapper for data provided from the [BanklyBaseUrl]
 */
@Serializable
data class NetworkResponse<T>(
    val hasResult: Boolean? = null,
    val result: T? = null,
    val successful: Boolean? = null,
    val resultType: Int? = null,
    val message: String? = null,
    val validationMessages: List<String>? = null,
    val responseCode: String? = null,
)

