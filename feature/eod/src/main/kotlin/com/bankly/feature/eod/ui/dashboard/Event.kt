package com.bankly.feature.eod.ui.dashboard

internal sealed interface EodDashboardScreenEvent {
    object LoadUiData : EodDashboardScreenEvent
}
