package com.bankly.feature.dashboard.ui.home

data class HomeScreenState(
    val accountBalance: Double = 0.00,
    val bankName: String = "",
    val accountNumber: String = "",
    val accountName: String = "",
    val shouldShowWalletBalance: Boolean = false,
    val message: String = "",
    val shouldShowErrorDialog: Boolean = false,
    val shouldShowLoadingIcon: Boolean = true,
    val shouldShowVisibilityIcon: Boolean = false
)