package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class MessageResult(
    val message: String? = null,
)
