package com.bankly.core.model

data class ValidateOtp(
    val otp: String,
    val phoneNumber: String,
)

