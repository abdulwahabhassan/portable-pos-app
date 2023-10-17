package com.bankly.feature.dashboard.ui.transactions

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.Transaction
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.dashboard.model.TransactionCategoryTab

internal data class TransactionsScreenState(
    val selectedCategoryTab: TransactionCategoryTab = TransactionCategoryTab.ALL,
    val searchQuery: String = "",
    val isTransactionsLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val transactions: List<Transaction> = emptyList()
)

internal sealed interface TransactionsScreenOneShotState : OneShotState {
    data class GoToTransactionDetailsScreen(val transaction: TransactionReceipt.TransactionHistory) :
        TransactionsScreenOneShotState
    data class UpdateLoadingIndicator(val isLoading: Boolean) :
        TransactionsScreenOneShotState
}
