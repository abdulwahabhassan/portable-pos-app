package com.bankly.feature.dashboard.ui.home

import com.bankly.core.common.viewmodel.OneShotState

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

sealed interface HomeScreenOneShotState : OneShotState