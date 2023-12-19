package com.bankly.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenApiError(
    @SerialName("error") val error: String? = null,
    @SerialName("error_description") val errorDescription: String? = null,
    @SerialName("status") val status: Long? = null,
    @SerialName("message") val message: String? = null,
)
