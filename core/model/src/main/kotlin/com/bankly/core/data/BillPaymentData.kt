package com.bankly.core.data

data class BillPaymentData(
    val billItemId: String,
    val billId: String,
    val amount: String,
    val currency: String,
    val paidFor: String,
    val paidForPhone: String,
    val paidForName: String,
    val otp: String,
    val channel: String,
    val serialNumber: String,
    val terminalId: String,
    val clientRequestId: String,
    val deviceId: String,
)
