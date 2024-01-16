package com.bankly.core.data.datastore

import com.bankly.core.model.entity.Feature
import com.bankly.core.model.entity.TransactionFilter
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class UserPreferences(
    val token: String = "",
    val shouldShowWalletBalance: Boolean = false,
    val remoteTransactionFilter: TransactionFilter = TransactionFilter(),
    val eodTransactionFilter: TransactionFilter = TransactionFilter(
        dateFrom = LocalDate.now().toKotlinLocalDate(),
        dateTo = LocalDate.now().toKotlinLocalDate()
    ),
    val featureToggleList: List<Feature> = Feature.values().toList()
        .filterNot { it is Feature.Settings },
    val fcmDeviceToken: String = ""
)
