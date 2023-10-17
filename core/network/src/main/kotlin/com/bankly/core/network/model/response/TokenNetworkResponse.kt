package com.bankly.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenNetworkResponse(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("expires_in")
    val expiresIn: Long? = null,
    @SerialName("token_type")
    val tokenType: String? = null,
    @SerialName("scope")
    val scope: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
)