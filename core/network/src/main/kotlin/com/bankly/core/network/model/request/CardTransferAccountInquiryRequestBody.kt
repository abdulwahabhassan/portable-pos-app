package com.bankly.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
class CardTransferAccountInquiryRequestBody(
    val bankId: Long,
    val accountNumber: String,
    val amount: Double,
    val serialNumber: String,
    val terminalId: String,
    val channel: String,
    val geoLocation: String,
    val deviceType: String,
)
