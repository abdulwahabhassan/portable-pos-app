package com.bankly.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenApiResponse(
    @SerialName("access_token")
    val accessToken: String?,
    @SerialName("expires_in")
    val expiresIn: Long?,
    @SerialName("token_type")
    val tokenType: String?,
    @SerialName("scope")
    val scope: String?,
)