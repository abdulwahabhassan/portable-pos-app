package com.bankly.core.data

data class ValidateElectricityMeterNumberData(
    val meterNumber: String,
    val billId: Long,
    val billItemId: Long,
)
