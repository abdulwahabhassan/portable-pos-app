package com.bankly.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val message: String? = null,
    val validationMessages: List<String>? = null,
    val errorCode: Int? = null,
)
