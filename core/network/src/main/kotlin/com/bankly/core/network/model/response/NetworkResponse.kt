package com.bankly.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wrapper for data provided from the [BanklyBaseUrl]
 */
@Serializable
data class NetworkResponse<T>(
    val hasResult: Boolean?,
    val result: T?,
    val successful: Boolean?,
    val resultType: String?,
    val message: String?,
    val validationMessages: List<String>?
)


@Serializable
data class TokenNetworkResponse(
    @SerialName("access_token")
    val accessToken: String?,
    @SerialName("expires_in")
    val expiresIn: Long? = null,
    @SerialName("token_type")
    val tokenType: String? = null,
    @SerialName("scope")
    val scope: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null
)