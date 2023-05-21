package com.bankly.core.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPassCodeRequestBody(
    @SerialName("userName")
    val phoneNumber: String
)
