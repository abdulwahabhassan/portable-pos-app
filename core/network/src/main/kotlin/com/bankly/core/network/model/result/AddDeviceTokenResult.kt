package com.bankly.core.network.model.result

class AddDeviceTokenResult(
    val id: Long,
    val userId: String,
    val deviceId: String,
    val provider: String
)