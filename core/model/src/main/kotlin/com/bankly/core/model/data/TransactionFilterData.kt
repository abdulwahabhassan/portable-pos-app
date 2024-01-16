package com.bankly.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class TransactionFilterData(
    val dateCreatedFrom: String = "",
    val dateCreatedTo: String = "",
    val transactionType: String = "",
)
