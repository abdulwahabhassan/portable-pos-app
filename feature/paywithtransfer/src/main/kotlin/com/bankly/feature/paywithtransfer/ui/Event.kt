package com.bankly.feature.paywithtransfer.ui

import com.bankly.core.entity.RecentFund

internal sealed interface PayWithTransferScreenEvent {
    object CloseRecentFundSummaryDialog : PayWithTransferScreenEvent
    data class OnRecentFundSelected (val recentFund: RecentFund) : PayWithTransferScreenEvent
    data class OnAccountDetailsExpandButtonClick(val isExpanded: Boolean) : PayWithTransferScreenEvent
    object DismissErrorDialog : PayWithTransferScreenEvent
}
