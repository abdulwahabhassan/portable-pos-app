package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticatedUserResult(
    val userId: String? = null,
    val message: String? = null,
)
