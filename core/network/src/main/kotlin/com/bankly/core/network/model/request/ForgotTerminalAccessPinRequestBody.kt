package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ForgotTerminalAccessPinRequestBody(
    val serialNumber: String,
)
