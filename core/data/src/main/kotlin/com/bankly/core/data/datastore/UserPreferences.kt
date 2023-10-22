package com.bankly.core.data.datastore

import com.bankly.core.entity.TransactionFilter
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val token: String = "",
    val shouldShowWalletBalance: Boolean = false,
    val terminalSerialNumber: String = "P260300061091",
    val transactionFilter: TransactionFilter = TransactionFilter(),
)
