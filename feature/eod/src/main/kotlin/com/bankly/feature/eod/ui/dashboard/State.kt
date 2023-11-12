package com.bankly.feature.eod.ui.dashboard

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.model.TransactionCategoryTab
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.CashFlow
import com.bankly.core.entity.Transaction
import com.bankly.core.entity.TransactionFilterType
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.datetime.LocalDate

internal data class EodDashboardScreenState(
    val totalTransactionAmount: Double = 0.00,
    val totalSuccessfulTransactionAmount: Double = 0.00,
    val totalFailedTransactionAmount: Double = 0.00,
    private val isTransactionSummaryLoading: Boolean = false
) {
    val isLoading: Boolean
        get() = isTransactionSummaryLoading
}

internal sealed interface EodDashboardScreenOneShotState : OneShotState {
    data class ShowErrorDialog(val message: String) : EodDashboardScreenOneShotState
}