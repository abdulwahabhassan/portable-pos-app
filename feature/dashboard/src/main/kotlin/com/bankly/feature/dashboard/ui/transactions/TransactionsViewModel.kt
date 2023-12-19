package com.bankly.feature.dashboard.ui.transactions

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.TransactionFilterData
import com.bankly.core.data.datastore.UserPreferences
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetTransactionFilterTypesUseCase
import com.bankly.core.domain.usecase.GetTransactionsUseCase
import com.bankly.core.entity.CashFlow
import com.bankly.core.entity.Transaction
import com.bankly.core.entity.TransactionFilter
import com.bankly.core.entity.TransactionFilterType
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.onFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class TransactionsViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getTransactionFilterTypesUseCase: GetTransactionFilterTypesUseCase,
) : BaseViewModel<TransactionsScreenEvent, TransactionsScreenState, TransactionsScreenOneShotState>(
    TransactionsScreenState(),
) {

    override suspend fun handleUiEvents(event: TransactionsScreenEvent) {
        when (event) {
            is TransactionsScreenEvent.OnCategoryTabSelected -> {
                setUiState { copy(selectedCategoryTab = event.category) }
            }

            is TransactionsScreenEvent.OnInputSearchQuery -> {
                setUiState { copy(searchQuery = event.query) }
            }

            TransactionsScreenEvent.DismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }

            TransactionsScreenEvent.LoadUiData -> {
                val userPrefData = userPreferencesDataStore.data()
                loadUiData(
                    filter = TransactionFilterData(
                        dateCreatedFrom = userPrefData.transactionFilter.dateFrom?.toString() ?: "",
                        dateCreatedTo = userPrefData.transactionFilter.dateTo?.toString() ?: "",
                        transactionType = if (userPrefData.transactionFilter.transactionTypes.size == 1) {
                            userPrefData.transactionFilter.transactionTypes[0].id.toString()
                        } else {
                            ""
                        },
                    ),
                )
            }

            is TransactionsScreenEvent.OnCashFlowFilterChipClick -> {
                setUiState {
                    copy(
                        cashFlows = event.cashFlows.map { cashFlowFilter ->
                            if (cashFlowFilter.title == event.cashFlow.title) {
                                when (val filter = event.cashFlow) {
                                    is CashFlow.Credit -> filter.copy(state = filter.state.not())
                                    is CashFlow.Debit -> filter.copy(state = filter.state.not())
                                }
                            } else {
                                cashFlowFilter
                            }
                        },
                    )
                }
            }

            is TransactionsScreenEvent.OnInputAccountName -> {
                setUiState { copy(accountNameTFV = event.accountNameTFV) }
            }

            is TransactionsScreenEvent.OnInputTransactionReference -> {
                setUiState { copy(transactionReferenceTFV = event.transactionReferenceTFV) }
            }

            TransactionsScreenEvent.OnShowLessFilterTypesClick -> {
                setUiState { copy(showAllTransactionFilterType = false) }
            }

            TransactionsScreenEvent.OnShowMoreFilterTypesClick -> {
                setUiState { copy(showAllTransactionFilterType = true) }
            }

            is TransactionsScreenEvent.DateFilterClick -> {
                setUiState {
                    copy(showDatePicker = true, whichDateRange = event.dateRange)
                }
            }

            is TransactionsScreenEvent.OnDateSelected -> {
                setUiState {
                    when (event.whichDateRange) {
                        DateRange.START_DATE -> copy(
                            startDateFilter = event.date,
                            showDatePicker = false,
                            whichDateRange = null,
                        )

                        DateRange.END_DATE -> copy(
                            endDateFilter = event.date,
                            showDatePicker = false,
                            whichDateRange = null,
                        )
                    }
                }
            }

            TransactionsScreenEvent.OnDismissDatePicker -> {
                setUiState { copy(showDatePicker = false, whichDateRange = null) }
            }

            is TransactionsScreenEvent.OnTransactionFilterTypeSelected -> {
                val list =
                    event.transactionFilterTypes.map { transactionFilterType: TransactionFilterType ->
                        if (transactionFilterType.id == event.transactionFilterType.id) {
                            transactionFilterType.copy(isSelected = transactionFilterType.isSelected.not())
                        } else {
                            transactionFilterType
                        }
                    }
                setUiState { copy(allTransactionFilterTypes = list) }
            }

            is TransactionsScreenEvent.OnApplyFilterClick -> {
                val filter = event.transactionFilter
                val isOneTransactionTypeSelected =
                    filter.transactionTypes.filter { it.isSelected }.size == 1

                userPreferencesDataStore.update {
                    copy(transactionFilter = filter)
                }
                loadUiData(
                    filter = TransactionFilterData(
                        dateCreatedFrom = filter.dateFrom?.toString() ?: "",
                        filter.dateTo?.toString() ?: "",
                        if (isOneTransactionTypeSelected) filter.transactionTypes.find { it.isSelected }?.id.toString() else "",
                    ),
                )
            }

            is TransactionsScreenEvent.RemoveTransactionTypeFilterItem -> {
                userPreferencesDataStore.update {
                    copy(
                        transactionFilter = transactionFilter.copy(
                            transactionTypes = transactionFilter.transactionTypes.map { type ->
                                if (type.id == event.item.id) {
                                    type.copy(
                                        isSelected = type.isSelected.not(),
                                    )
                                } else {
                                    type
                                }
                            },
                        ),
                    )
                }
            }

            TransactionsScreenEvent.OnClearAllFilters -> {
                userPreferencesDataStore.update {
                    copy(transactionFilter = TransactionFilter())
                }
                loadUiData()
            }

            TransactionsScreenEvent.OnRefresh -> {
                val userPrefData = userPreferencesDataStore.data()
                loadUiData(
                    filter = TransactionFilterData(
                        dateCreatedFrom = userPrefData.transactionFilter.dateFrom?.toString() ?: "",
                        dateCreatedTo = userPrefData.transactionFilter.dateTo?.toString() ?: "",
                        transactionType = if (userPrefData.transactionFilter.transactionTypes.size == 1) {
                            userPrefData.transactionFilter.transactionTypes[0].id.toString()
                        } else {
                            ""
                        },
                    ),
                    isTriggeredByRefresh = true,
                )
            }
        }
    }

    private suspend fun loadUiData(filter: TransactionFilterData? = null, isTriggeredByRefresh: Boolean = false) {
        combine(
            flow = getTransactionsUseCase.invoke(
                token = userPreferencesDataStore.data().token,
                minimum = 1,
                maximum = 100,
                filter = filter ?: TransactionFilterData(),
            ),
            flow2 = getTransactionFilterTypesUseCase.invoke(
                token = userPreferencesDataStore.data().token,
            ),
            flow3 = userPreferencesDataStore.flow(),
        ) { transactions, transactionFilterTypes, userPreferences ->
            Triple(transactions, transactionFilterTypes, userPreferences)
        }.onEach { triple: Triple<Resource<List<Transaction>>, Resource<List<TransactionFilterType>>, UserPreferences> ->
            if (triple.first is Resource.Loading || triple.second is Resource.Loading) {
                val transactionFilter = triple.third.transactionFilter
                setUiState {
                    copy(
                        isTransactionsLoading = triple.first == Resource.Loading,
                        isTransactionFilterTypesLoading = triple.second == Resource.Loading,
                        startDateFilter = transactionFilter.dateFrom,
                        endDateFilter = transactionFilter.dateTo,
                        cashFlows = transactionFilter.cashFlows,
                        transactionReferenceTFV = TextFieldValue(transactionFilter.transactionReference),
                        accountNameTFV = TextFieldValue(transactionFilter.accountName),
                        allTransactionFilterTypes = transactionFilter.transactionTypes,
                        selectedTransactionFilterTypes = transactionFilter.transactionTypes.filter { type -> type.isSelected },
                        isRefreshing = isTriggeredByRefresh,
                    )
                }
            }
            if (triple.first is Resource.Ready && triple.second is Resource.Ready) {
                val selectedTransactionFilterTypes =
                    triple.third.transactionFilter.transactionTypes.filter { it.isSelected }
                val allTransactionFilterTypes =
                    (triple.second as Resource.Ready<List<TransactionFilterType>>).data.distinct()
                        .map { type ->
                            selectedTransactionFilterTypes.find { selectedType ->
                                type.id == selectedType.id
                            } ?: type
                        }
                setUiState {
                    copy(
                        isTransactionFilterTypesLoading = false,
                        allTransactionFilterTypes = allTransactionFilterTypes,
                        selectedTransactionFilterTypes = selectedTransactionFilterTypes,
                        isTransactionsLoading = false,
                        transactions = filterTransactions(
                            transactions = (triple.first as Resource.Ready<List<Transaction>>).data,
                            userPrefData = triple.third,
                        ),
                        isRefreshing = false,
                    )
                }
            }

            if (triple.first is Resource.SessionExpired || triple.second is Resource.SessionExpired) {
                setOneShotState(TransactionsScreenOneShotState.OnSessionExpired)
            }

            triple.first.onFailure { message: String ->
                setUiState {
                    copy(
                        isTransactionsLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = message,
                        isRefreshing = false,
                    )
                }
            }

            triple.second.onFailure { message: String ->
                setUiState {
                    copy(
                        isTransactionFilterTypesLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = message,
                        isRefreshing = false,
                    )
                }
            }
        }.catch {
            it.printStackTrace()
            setUiState {
                copy(
                    isTransactionFilterTypesLoading = false,
                    showErrorDialog = true,
                    errorDialogMessage = it.message ?: "",
                    isRefreshing = false,
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun filterTransactions(
        transactions: List<Transaction>,
        userPrefData: UserPreferences,
    ): List<Transaction> {
        return transactions
            .filter { transaction: Transaction ->
                val selectedTypeIDs = userPrefData.transactionFilter.transactionTypes
                    .filter { type: TransactionFilterType -> type.isSelected }
                    .map { type: TransactionFilterType -> type.id }
                if (selectedTypeIDs.isEmpty()) {
                    true
                } else {
                    selectedTypeIDs.any { id: Long -> id == transaction.transactionType }
                }
            }
            .filter { transaction: Transaction ->
                val selectedCashFlows = userPrefData.transactionFilter.cashFlows
                    .filter { cashFlow: CashFlow -> cashFlow.isSelected }
                if (selectedCashFlows.isEmpty()) {
                    true
                } else {
                    selectedCashFlows.any { cashFlow: CashFlow ->
                        when (cashFlow) {
                            is CashFlow.Credit -> transaction.isCredit
                            is CashFlow.Debit -> transaction.isDebit
                        }
                    }
                }
            }
            .filter { transaction: Transaction ->
                transaction.reference.contains(
                    userPrefData.transactionFilter.transactionReference,
                    true,
                )
            }
            .filter { transaction: Transaction ->
                transaction.receiverName.contains(
                    userPrefData.transactionFilter.accountName,
                    true,
                ) || transaction.senderName.contains(
                    userPrefData.transactionFilter.accountName,
                    true,
                )
            }
    }
}
