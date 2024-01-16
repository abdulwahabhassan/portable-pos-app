package com.bankly.core.model.data

data class ResetPassCodeData(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val code: String,
)
