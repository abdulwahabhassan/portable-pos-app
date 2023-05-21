package com.bankly.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticatedUser(
    val userId: String? = null,
    val message: String? = null
)
