package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
class AddDeviceTokenRequestBody(
    val userId: String,
    val deviceId: String
)