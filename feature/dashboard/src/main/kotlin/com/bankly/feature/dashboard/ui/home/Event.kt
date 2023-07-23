package com.bankly.feature.dashboard.ui.home

sealed interface HomeScreenEvent {
    data class ToggleWalletBalanceVisibility(val shouldShowWalletBalance: Boolean) : HomeScreenEvent
    object OnDismissErrorDialog : HomeScreenEvent
}