package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ValidateElectricityMeterNumberRequestBody(
    val meterNumber: String,
    val billId: Long,
    val billItemId: Long,
)
