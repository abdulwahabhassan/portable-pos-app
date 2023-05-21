package com.bankly.core.network.model

import kotlinx.serialization.Serializable


@Serializable
data class ResultStatus(
    val status: Boolean? = null
)
