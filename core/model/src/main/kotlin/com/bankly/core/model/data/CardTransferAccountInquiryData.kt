package com.bankly.core.model.data

class CardTransferAccountInquiryData(
    val bankId: Long,
    val accountNumber: String,
    val amount: Double,
    val serialNumber: String,
    val terminalId: String,
    val channel: String,
    val geoLocation: String,
    val deviceType: String,
)
