package com.bankly.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ResultMessage(
    val message: String? = null
)
