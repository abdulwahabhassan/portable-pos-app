package com.bankly.core.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTokenRequestBody(
    @SerialName("grant_type")
    val grantType: String,

    val username: String,
    val password: String,

    @SerialName("client_id")
    val clientID: String,

    @SerialName("client_secret")
    val clientSecret: String
)
