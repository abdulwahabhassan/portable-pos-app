package com.bankly.core.model

data class ChangePassCode(
    val serialNumber: String,
    val oldPasscode: String,
    val newPasscode: String,
    val confirmPasscode: String
)
