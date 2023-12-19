package com.bankly.feature.eod.ui.dashboard

import com.bankly.core.common.viewmodel.OneShotState

internal data class EodDashboardScreenState(
    val totalTransactionAmount: Double = 0.00,
    val totalSuccessfulTransactionAmount: Double = 0.00,
    val totalFailedTransactionAmount: Double = 0.00,
    private val isTransactionSummaryLoading: Boolean = false,
) {
    val isLoading: Boolean
        get() = isTransactionSummaryLoading
}

internal sealed interface EodDashboardScreenOneShotState : OneShotState {
    data class ShowErrorDialog(val message: String) : EodDashboardScreenOneShotState
}
