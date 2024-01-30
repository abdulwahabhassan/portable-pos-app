package com.bankly.feature.networkchecker.ui

import com.bankly.feature.networkchecker.model.NetworkCheckerTab

internal sealed interface NetworkCheckerScreenEvent {
    object DismissErrorDialog : NetworkCheckerScreenEvent
    class OnTabSelected(val tab: NetworkCheckerTab) : NetworkCheckerScreenEvent
    object LoadUiData : NetworkCheckerScreenEvent
    data class OnInputSearchQuery(
        val query: String,
        val selectedTab: NetworkCheckerTab,
    ) : NetworkCheckerScreenEvent
}
