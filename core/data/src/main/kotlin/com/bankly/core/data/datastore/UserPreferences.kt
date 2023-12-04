package com.bankly.core.data.datastore

import com.bankly.core.entity.Feature
import com.bankly.core.entity.TransactionFilter
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val token: String = "",
    val shouldShowWalletBalance: Boolean = false,
    val transactionFilter: TransactionFilter = TransactionFilter(),
    val featureToggleList: List<Feature> = Feature.values().toList()
)
