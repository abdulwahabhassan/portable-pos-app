package com.bankly.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkError(
    val message: String?,
    val validationMessages: List<String>?,
    val errorCode: Int?
)

@Serializable
data class TokenNetworkError(
    @SerialName("error") val error: String?,
    @SerialName("error_description") val errorDescription: String?,
    @SerialName("status") val status: Long?,
    @SerialName("message") val message: String?
)