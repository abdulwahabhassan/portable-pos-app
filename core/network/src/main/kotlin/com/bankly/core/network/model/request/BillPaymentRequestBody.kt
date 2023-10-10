package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class BillPaymentRequestBody(
    val billItemId: String,
    val billId: String,
    val amount: String,
    val currency: String,
    val paidFor: String,
    val otp: String,
    val channel: String,
    val serialNumber: String,
    val terminalId: String,
    val clientRequestId: String,
    val deviceId: String,
    val paidForPhone: String,
    val paidForName: String
)
