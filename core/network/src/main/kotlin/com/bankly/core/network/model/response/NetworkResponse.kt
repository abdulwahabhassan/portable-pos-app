package com.bankly.core.network.model.response

import kotlinx.serialization.SerialName
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
    val responseCode: String? = null
)


@Serializable
data class TokenNetworkResponse(
    @SerialName("access_token")
    val accessToken: String?= null,
    @SerialName("expires_in")
    val expiresIn: Long? = null,
    @SerialName("token_type")
    val tokenType: String? = null,
    @SerialName("scope")
    val scope: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null
)