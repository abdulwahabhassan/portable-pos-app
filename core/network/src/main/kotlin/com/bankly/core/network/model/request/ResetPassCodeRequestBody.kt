package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPassCodeRequestBody(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val code: String
)

/**
 *
{
"username": "08167039661",
"password": "Gdz36Val",
"confirmPassword": "Gdz36Val",
"code": "452262"
}
 */
