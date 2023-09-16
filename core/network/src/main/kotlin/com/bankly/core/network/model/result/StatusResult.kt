package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class StatusResult(
    val status: Boolean? = null,
)
