package com.bankly.core.data.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val token: String = "",
    val shouldShowWalletBalance: Boolean = false
)