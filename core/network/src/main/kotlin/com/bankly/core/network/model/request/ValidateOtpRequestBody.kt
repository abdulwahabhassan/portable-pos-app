package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ValidateOtpRequestBody(
    val otp: String,
    val phoneNumber: String,
)
