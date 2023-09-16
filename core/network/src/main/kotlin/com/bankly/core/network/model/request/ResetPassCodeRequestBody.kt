package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPassCodeRequestBody(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val code: String,
)
