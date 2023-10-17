package com.bankly.feature.dashboard.ui.transactions

import com.bankly.core.entity.Transaction
import com.bankly.feature.dashboard.model.TransactionCategoryTab

internal sealed interface TransactionsScreenEvent {
    data class OnCategoryTabSelected(val category: TransactionCategoryTab) : TransactionsScreenEvent

    data class OnInputSearchQuery(
        val query: String,
    ) : TransactionsScreenEvent

    data class OnTransactionSelected(val transaction: Transaction) : TransactionsScreenEvent
    object DismissErrorDialog : TransactionsScreenEvent
    object FetchTransactions : TransactionsScreenEvent
}
