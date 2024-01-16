package com.bankly.feature.dashboard.ui.home

import com.bankly.core.model.entity.Feature

internal sealed interface HomeScreenEvent {
    data class ToggleWalletBalanceVisibility(val shouldShowWalletBalance: Boolean) : HomeScreenEvent
    object OnDismissErrorDialog : HomeScreenEvent
    object FetchWalletBalance : HomeScreenEvent
    data class OnFeatureCardClick(val feature: com.bankly.core.model.entity.Feature) : HomeScreenEvent
    object OnDismissFeatureAccessDeniedDialog : HomeScreenEvent
    object OnRefresh : HomeScreenEvent
}
