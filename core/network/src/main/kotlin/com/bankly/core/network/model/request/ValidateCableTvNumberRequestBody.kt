package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ValidateCableTvNumberRequestBody(
    val cardNumber: String,
    val billId: Long,
)
