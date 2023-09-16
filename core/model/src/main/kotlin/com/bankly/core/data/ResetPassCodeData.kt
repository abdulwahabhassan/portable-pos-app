package com.bankly.core.data

data class ResetPassCodeData(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val code: String,
)
