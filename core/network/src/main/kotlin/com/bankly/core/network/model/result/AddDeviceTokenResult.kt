package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
class AddDeviceTokenResult(
    val id: Long,
    val userId: String,
    val deviceId: String,
    val provider: Long
)