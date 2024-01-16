package com.bankly.core.model.data

data class ValidateElectricityMeterNumberData(
    val meterNumber: String,
    val billId: Long,
    val billItemId: Long,
)
