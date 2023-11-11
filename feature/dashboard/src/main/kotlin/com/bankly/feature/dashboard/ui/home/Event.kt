package com.bankly.feature.dashboard.ui.home

internal sealed interface HomeScreenEvent {
    data class ToggleWalletBalanceVisibility(val shouldShowWalletBalance: Boolean) : HomeScreenEvent
    object OnDismissErrorDialog : HomeScreenEvent
    object FetchWalletBalance : HomeScreenEvent
}
