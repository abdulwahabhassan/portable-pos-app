package com.bankly.core.model.entity

class AddDeviceToken(
    val id: Long,
    val userId: String,
    val deviceId: String,
    val provider: String
)