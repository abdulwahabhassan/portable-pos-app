package com.bankly.feature.eod.ui.eodtransactions

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.model.TransactionCategoryTab
import com.bankly.core.model.entity.CashFlow
import com.bankly.core.model.entity.TransactionFilter
import com.bankly.core.model.entity.TransactionFilterType
import kotlinx.datetime.LocalDate

internal sealed interface EodTransactionsScreenEvent {
    data class OnCategoryTabSelected(val category: TransactionCategoryTab) :
        EodTransactionsScreenEvent

    data class OnInputSearchQuery(
        val query: String,
    ) : EodTransactionsScreenEvent

    class OnInputTransactionReference(val transactionReferenceTFV: TextFieldValue) :
        EodTransactionsScreenEvent

    class OnInputAccountName(val accountNameTFV: TextFieldValue) : EodTransactionsScreenEvent
    class OnCashFlowFilterChipClick(val cashFlow: CashFlow, val cashFlows: List<CashFlow>) :
        EodTransactionsScreenEvent

    class DateFilterClick(val dateRange: DateRange) : EodTransactionsScreenEvent

    object DismissErrorDialog : EodTransactionsScreenEvent
    object LoadUiData : EodTransactionsScreenEvent
    object OnShowLessFilterTypesClick : EodTransactionsScreenEvent
    object OnShowMoreFilterTypesClick : EodTransactionsScreenEvent
    object OnDismissDatePicker : EodTransactionsScreenEvent
    object OnClearAllFilters : EodTransactionsScreenEvent
    data class OnDateSelected(val date: LocalDate, val whichDateRange: DateRange) :
        EodTransactionsScreenEvent

    class OnTransactionFilterTypeSelected(
        val transactionFilterType: TransactionFilterType,
        val transactionFilterTypes: List<TransactionFilterType>,
    ) :
        EodTransactionsScreenEvent

    class OnApplyFilterClick(val transactionFilter: TransactionFilter) : EodTransactionsScreenEvent
    class RemoveTransactionTypeFilterItem(val item: TransactionFilterType) :
        EodTransactionsScreenEvent
    object RemoveTransactionReferenceItem : EodTransactionsScreenEvent
    object RemoveAccountNameItem : EodTransactionsScreenEvent
    class RemoveCashFlowItem(val cashFlow: CashFlow) : EodTransactionsScreenEvent
    class RemoveDateItem(val whichDate: DateRange) : EodTransactionsScreenEvent
    object OnRefresh : EodTransactionsScreenEvent
}
