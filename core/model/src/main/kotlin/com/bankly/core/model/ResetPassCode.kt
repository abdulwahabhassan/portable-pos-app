package com.bankly.core.model

data class ResetPassCode(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val code: String
)
