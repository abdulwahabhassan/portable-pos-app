package com.bankly.feature.dashboard.ui.transactions

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.model.TransactionCategoryTab
import com.bankly.core.entity.CashFlow
import com.bankly.core.entity.TransactionFilter
import com.bankly.core.entity.TransactionFilterType
import kotlinx.datetime.LocalDate

internal sealed interface TransactionsScreenEvent {
    data class OnCategoryTabSelected(val category: TransactionCategoryTab) : TransactionsScreenEvent

    data class OnInputSearchQuery(
        val query: String,
    ) : TransactionsScreenEvent

    class OnInputTransactionReference(val transactionReferenceTFV: TextFieldValue) :
        TransactionsScreenEvent

    class OnInputAccountName(val accountNameTFV: TextFieldValue) : TransactionsScreenEvent
    class OnCashFlowFilterChipClick(val cashFlow: CashFlow, val cashFlows: List<CashFlow>) :
        TransactionsScreenEvent

    class DateFilterClick(val dateRange: DateRange) : TransactionsScreenEvent

    object DismissErrorDialog : TransactionsScreenEvent
    object LoadUiData : TransactionsScreenEvent
    object OnShowLessFilterTypesClick : TransactionsScreenEvent
    object OnShowMoreFilterTypesClick : TransactionsScreenEvent
    object OnDismissDatePicker : TransactionsScreenEvent
    object OnClearAllFilters : TransactionsScreenEvent
    data class OnDateSelected(val date: LocalDate, val whichDateRange: DateRange) :
        TransactionsScreenEvent

    class OnTransactionFilterTypeSelected(
        val transactionFilterType: TransactionFilterType,
        val transactionFilterTypes: List<TransactionFilterType>,
    ) :
        TransactionsScreenEvent

    class OnApplyFilterClick(val transactionFilter: TransactionFilter) : TransactionsScreenEvent
    class RemoveTransactionTypeFilterItem(val item: TransactionFilterType) : TransactionsScreenEvent

    object OnRefresh : TransactionsScreenEvent
}
