package com.bankly.feature.dashboard.ui.home

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.Feature

data class HomeScreenState(
    val accountBalance: Double = 0.00,
    val bankName: String = "",
    val accountNumber: String = "",
    val accountName: String = "",
    val shouldShowWalletBalance: Boolean = false,
    val message: String = "",
    val shouldShowErrorDialog: Boolean = false,
    val shouldShowLoadingIcon: Boolean = true,
    val shouldShowVisibilityIcon: Boolean = false,
    val showFeatureAccessDeniedDialog: Boolean = false
)

sealed interface HomeScreenOneShotState : OneShotState {
    object OnSessionExpired : HomeScreenOneShotState
    data class GoToFeature(val feature: Feature) : HomeScreenOneShotState
}
