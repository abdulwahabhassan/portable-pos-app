package com.bankly.feature.eod.ui.synceod

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.model.TransactionCategoryTab
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.CashFlow
import com.bankly.core.entity.Transaction
import com.bankly.core.entity.TransactionFilterType
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.datetime.LocalDate

internal data class SyncEodScreenState(
    val processedAmount: Double = 0.00,
    val settledAmount: Double = 0.00,
    private val isEodInfoLoading: Boolean = false,
    val isEodSyncLoading: Boolean = false,
    val errorDialogMessage: String = "",
    val showErrorDialog: Boolean = false,
    val successfulEodSyncMessage: String = "",
    val showSuccessfulEodSyncDialog: Boolean = false,
) {
    val isLoading: Boolean
        get() = isEodInfoLoading || isEodSyncLoading

    val isUserInputEnabled: Boolean
        get() = isLoading.not()
}

internal sealed interface SyncEodScreenOneShotState : OneShotState {
    object OnSessionExpired: SyncEodScreenOneShotState
}
