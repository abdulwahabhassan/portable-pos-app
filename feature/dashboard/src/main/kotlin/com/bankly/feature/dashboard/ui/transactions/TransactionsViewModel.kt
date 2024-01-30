package com.bankly.feature.dashboard.ui.transactions

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferences
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetTransactionFilterTypesUseCase
import com.bankly.core.domain.usecase.GetTransactionsUseCase
import com.bankly.core.model.data.TransactionFilterData
import com.bankly.core.model.entity.CashFlow
import com.bankly.core.model.entity.Transaction
import com.bankly.core.model.entity.TransactionFilter
import com.bankly.core.model.entity.TransactionFilterType
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.onFailure
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
                        dateCreatedFrom = userPrefData.remoteTransactionFilter.dateFrom?.toString()
                            ?: "",
                        dateCreatedTo = userPrefData.remoteTransactionFilter.dateTo?.toString()
                            ?: "",
                        transactionType = if (userPrefData.remoteTransactionFilter.transactionTypes.size == 1) {
                            userPrefData.remoteTransactionFilter.transactionTypes[0].id.toString()
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
                    copy(remoteTransactionFilter = filter)
                }
                loadUiData(
                    filter = TransactionFilterData(
                        dateCreatedFrom = filter.dateFrom?.toString() ?: "",
                        dateCreatedTo = filter.dateTo?.toString() ?: "",
                        if (isOneTransactionTypeSelected) filter.transactionTypes.find { it.isSelected }?.id.toString() else "",
                    ),
                )
            }

            is TransactionsScreenEvent.RemoveTransactionTypeFilterItem -> {
                userPreferencesDataStore.update {
                    copy(
                        remoteTransactionFilter = remoteTransactionFilter.copy(
                            transactionTypes = remoteTransactionFilter.transactionTypes.map { type ->
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
                    copy(remoteTransactionFilter = TransactionFilter())
                }
                loadUiData()
            }

            TransactionsScreenEvent.OnRefresh -> {
                val userPrefData = userPreferencesDataStore.data()
                loadUiData(
                    filter = TransactionFilterData(
                        dateCreatedFrom = userPrefData.remoteTransactionFilter.dateFrom?.toString()
                            ?: "",
                        dateCreatedTo = userPrefData.remoteTransactionFilter.dateTo?.toString()
                            ?: "",
                        transactionType = if (userPrefData.remoteTransactionFilter.transactionTypes.size == 1) {
                            userPrefData.remoteTransactionFilter.transactionTypes[0].id.toString()
                        } else {
                            ""
                        },
                    ),
                    isTriggeredByRefresh = true,
                )
            }

            TransactionsScreenEvent.RemoveAccountNameItem -> userPreferencesDataStore.update {
                copy(remoteTransactionFilter = remoteTransactionFilter.copy(accountName = ""))
            }

            is TransactionsScreenEvent.RemoveCashFlowItem -> userPreferencesDataStore.update {
                copy(remoteTransactionFilter = remoteTransactionFilter.copy(cashFlows = remoteTransactionFilter.cashFlows.map {
                    if (it.title == event.cashFlow.title) {
                        when (it) {
                            is CashFlow.Credit -> it.copy(it.isSelected.not())
                            is CashFlow.Debit -> it.copy(it.isSelected.not())
                        }
                    } else it
                }))
            }

            is TransactionsScreenEvent.RemoveDateItem -> userPreferencesDataStore.update {
                copy(
                    remoteTransactionFilter = when (event.whichDate) {
                        DateRange.START_DATE -> remoteTransactionFilter.copy(dateFrom = null)
                        DateRange.END_DATE -> remoteTransactionFilter.copy(dateTo = null)
                    }
                )
            }

            TransactionsScreenEvent.RemoveTransactionReferenceItem -> userPreferencesDataStore.update {
                copy(remoteTransactionFilter = remoteTransactionFilter.copy(transactionReference = ""))
            }
        }
    }

    private suspend fun loadUiData(
        filter: TransactionFilterData? = null,
        isTriggeredByRefresh: Boolean = false,
    ) {
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
                val transactionFilter = triple.third.remoteTransactionFilter
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
                    triple.third.remoteTransactionFilter.transactionTypes.filter { it.isSelected }
                val allTransactionFilterTypes =
                    (triple.second as Resource.Ready<List<TransactionFilterType>>).data.distinct()
                        .map { type ->
                            selectedTransactionFilterTypes.find { selectedType ->
                                type.id == selectedType.id
                            } ?: type
                        }
                val transactionFilter = triple.third.remoteTransactionFilter
                setUiState {
                    copy(
                        isTransactionFilterTypesLoading = false,
                        startDateFilter = transactionFilter.dateFrom,
                        endDateFilter = transactionFilter.dateTo,
                        cashFlows = transactionFilter.cashFlows,
                        transactionReferenceTFV = TextFieldValue(transactionFilter.transactionReference),
                        accountNameTFV = TextFieldValue(transactionFilter.accountName),
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
                val selectedTypeIDs = userPrefData.remoteTransactionFilter.transactionTypes
                    .filter { type: TransactionFilterType -> type.isSelected }
                    .map { type: TransactionFilterType -> type.id }
                if (selectedTypeIDs.isEmpty()) {
                    true
                } else {
                    selectedTypeIDs.any { id: Long -> id == transaction.transactionTypeId }
                }
            }
            .filter { transaction: Transaction ->
                val selectedCashFlows = userPrefData.remoteTransactionFilter.cashFlows
                    .filter { cashFlow: CashFlow -> cashFlow.isSelected }
                if (selectedCashFlows.isEmpty()) {
                    true
                } else {
                    selectedCashFlows.any { cashFlow: CashFlow ->
                        when (cashFlow) {
                            is CashFlow.Credit -> transaction.isCreditTransaction
                            is CashFlow.Debit -> transaction.isDebitTransaction
                        }
                    }
                }
            }
            .filter { transaction: Transaction ->
                transaction.transactionReference.contains(
                    userPrefData.remoteTransactionFilter.transactionReference,
                    true,
                )
            }
            .filter { transaction: Transaction ->
                when (transaction) {
                    is Transaction.Eod -> when (transaction) {
                        is Transaction.Eod.BankTransfer -> transaction.beneficiaryAccountName.contains(
                            userPrefData.remoteTransactionFilter.accountName,
                            true,
                        )

                        is Transaction.Eod.BillPayment -> transaction.paidByAccountName.contains(
                            userPrefData.remoteTransactionFilter.accountName,
                            true,
                        )

                        is Transaction.Eod.CardPayment -> transaction.cardHolderName.contains(
                            userPrefData.remoteTransactionFilter.accountName,
                            true,
                        )

                        is Transaction.Eod.CardTransfer -> transaction.beneficiaryName.contains(
                            userPrefData.remoteTransactionFilter.accountName,
                            true,
                        ) || transaction.senderName.contains(
                            userPrefData.remoteTransactionFilter.accountName,
                            true,
                        )

                        is Transaction.Eod.PayWithTransfer -> transaction.receiverName.contains(
                            userPrefData.remoteTransactionFilter.accountName,
                            true,
                        ) || transaction.senderAccountName.contains(
                            userPrefData.remoteTransactionFilter.accountName,
                            true,
                        )
                    }

                    is Transaction.History -> transaction.receiverName.contains(
                        userPrefData.remoteTransactionFilter.accountName,
                        true,
                    ) || transaction.senderName.contains(
                        userPrefData.remoteTransactionFilter.accountName,
                        true,
                    )
                }
            }
    }
}
