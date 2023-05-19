package com.bankly.core.network.model.request

data class ChangePassCodeRequestBody(
    val serialNumber: String,
    val oldPasscode: String,
    val newPasscode: String,
    val confirmPasscode: String
)
