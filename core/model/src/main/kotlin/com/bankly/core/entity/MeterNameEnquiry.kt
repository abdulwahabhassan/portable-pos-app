package com.bankly.core.entity

data class MeterNameEnquiry (
    val meterNumber: String,
    val amount: Double,
    val customerName: String,
    val address: String,
    val meterType: Long,
    val packageName: String,
    val debtRepayment: Double
)