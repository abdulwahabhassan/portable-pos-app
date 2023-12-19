package com.bankly.feature.dashboard.ui.transactions

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.model.TransactionCategoryTab
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.CashFlow
import com.bankly.core.entity.Transaction
import com.bankly.core.entity.TransactionFilterType
import kotlinx.datetime.LocalDate

internal data class TransactionsScreenState(
    val selectedCategoryTab: TransactionCategoryTab = TransactionCategoryTab.ALL,
    val searchQuery: String = "",
    private val isTransactionsLoading: Boolean = false,
    private val isTransactionFilterTypesLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val transactions: List<Transaction> = emptyList(),
    val allTransactionFilterTypes: List<TransactionFilterType> = emptyList(),
    val isTransactionReferenceError: Boolean = false,
    val isAccountNameError: Boolean = false,
    val transactionReferenceFeedBack: String = "",
    val transactionReferenceTFV: TextFieldValue = TextFieldValue(),
    val isAccountNameFeedbackError: Boolean = false,
    val accountNameFeedback: String = "",
    val accountNameTFV: TextFieldValue = TextFieldValue(),
    val startDateFilter: LocalDate? = null,
    val endDateFilter: LocalDate? = null,
    val showDatePicker: Boolean = false,
    val whichDateRange: DateRange? = null,
    val startDateFeedBack: String = "",
    val endDateFeedBack: String = "",
    val isStartDateFilterError: Boolean = false,
    val isEndDateFilterError: Boolean = false,
    val showAllTransactionFilterType: Boolean = false,
    val cashFlows: List<CashFlow> = emptyList(),
    val selectedTransactionFilterTypes: List<TransactionFilterType> = emptyList(),
    val isRefreshing: Boolean = false,
) {

    val isLoading: Boolean
        get() = isTransactionsLoading || isTransactionFilterTypesLoading

    val isUserInputEnabled: Boolean
        get() = isTransactionsLoading.not() && isTransactionFilterTypesLoading.not()
}

internal sealed interface TransactionsScreenOneShotState : OneShotState {
    object OnSessionExpired : TransactionsScreenOneShotState
}
