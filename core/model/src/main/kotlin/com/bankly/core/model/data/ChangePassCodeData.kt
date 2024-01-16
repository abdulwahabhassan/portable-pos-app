package com.bankly.core.model.data

data class ChangePassCodeData(
    val serialNumber: String,
    val oldPasscode: String,
    val newPasscode: String,
    val confirmPasscode: String,
)
