package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ChangePassCodeRequestBody(
    val serialNumber: String,
    val oldPasscode: String,
    val newPasscode: String,
    val confirmPasscode: String
)
