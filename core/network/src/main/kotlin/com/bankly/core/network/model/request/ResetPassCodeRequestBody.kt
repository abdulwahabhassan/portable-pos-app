package com.bankly.core.network.model.request

data class ResetPassCodeRequestBody(
    val serialNumber: String,
    val defaultPasscode: String,
    val newPasscode: String,
    val confirmPasscode: String
)
