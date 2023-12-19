package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class MeterNameEnquiryResult(
    val meterNumber: String,
    val amount: Double,
    val customerName: String,
    val address: String,
    val meterType: Long,
    val packageName: String,
    val debtRepayment: Double,
)
