package com.bankly.core.data

data class BillPaymentData(
    val billItemId: String,
    val billId: String,
    val amount: String,
    val currency: String,
    val paidBy: String,
    val paidFor: String,
    val paidForName: String,
    val paidForEmail: String,
    val paidForPhone: String,
    val otp: String,
    val channel: String,
    val serialNumber: String,
    val terminalId: String,
    val clientRequestId: String,
    val deviceId: String
)
