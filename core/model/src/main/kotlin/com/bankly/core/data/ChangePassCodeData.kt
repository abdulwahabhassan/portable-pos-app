package com.bankly.core.data

data class ChangePassCodeData(
    val serialNumber: String,
    val oldPasscode: String,
    val newPasscode: String,
    val confirmPasscode: String
)
