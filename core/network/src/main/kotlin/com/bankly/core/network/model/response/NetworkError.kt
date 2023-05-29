package com.bankly.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkError(
    val message: String? = null,
    val validationMessages: List<String>? = null,
    val errorCode: Int? = null
)

@Serializable
data class TokenNetworkError(
    @SerialName("error") val error: String? = null,
    @SerialName("error_description") val errorDescription: String? = null,
    @SerialName("status") val status: Long? = null,
    @SerialName("message") val message: String? = null
)