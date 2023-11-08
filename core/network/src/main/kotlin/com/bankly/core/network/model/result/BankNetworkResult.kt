package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
class BankNetworkResult (
    val bankCode: String? = null,
    val bankName: String? = null,
    val totalCount: Long? = null,
    val countPercentage: Double? = null,
)
