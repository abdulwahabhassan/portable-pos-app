package com.bankly.core.network.retrofit.model

import kotlinx.serialization.Serializable

@Serializable
data class Any(
    val nil: String,
) : kotlin.Any()