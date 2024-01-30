package com.bankly.feature.eod.ui.eodtransactions

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.model.data.TransactionFilterData
import com.bankly.core.data.datastore.UserPreferences
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetEodTransactionsUseCase
import com.bankly.core.domain.usecase.GetTransactionFilterTypesUseCase
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
internal class EodTransactionsViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val getEodTransactionsUseCase: GetEodTransactionsUseCase,
    private val getTransactionFilterTypesUseCase: GetTransactionFilterTypesUseCase,
) : BaseViewModel<EodTransactionsScreenEvent, EodTransactionsScreenState, EodTransactionsScreenOneShotState>(
    EodTransactionsScreenState(),
) {

    override suspend fun handleUiEvents(event: EodTransactionsScreenEvent) {
        when (event) {
            is EodTransactionsScreenEvent.OnCategoryTabSelected -> {
                setUiState { copy(selectedCategoryTab = event.category) }
            }

            is EodTransactionsScreenEvent.OnInputSearchQuery -> {
                setUiState { copy(searchQuery = event.query) }
            }

            EodTransactionsScreenEvent.DismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }

            EodTransactionsScreenEvent.LoadUiData -> {
                val userPrefData = userPreferencesDataStore.data()
                loadUiData(
                    filter = TransactionFilterData(
                        dateCreatedFrom = userPrefData.eodTransactionFilter.dateFrom?.toString()
                            ?: "",
                        dateCreatedTo = userPrefData.eodTransactionFilter.dateTo?.toString() ?: "",
                        transactionType = if (userPrefData.eodTransactionFilter.transactionTypes.size == 1) {
                            userPrefData.eodTransactionFilter.transactionTypes[0].id.toString()
                        } else {
                            ""
                        },
                    ),
                )
            }

            is EodTransactionsScreenEvent.OnCashFlowFilterChipClick -> {
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

            is EodTransactionsScreenEvent.OnInputAccountName -> {
                setUiState { copy(accountNameTFV = event.accountNameTFV) }
            }

            is EodTransactionsScreenEvent.OnInputTransactionReference -> {
                setUiState { copy(transactionReferenceTFV = event.transactionReferenceTFV) }
            }

            EodTransactionsScreenEvent.OnShowLessFilterTypesClick -> {
                setUiState { copy(showAllTransactionFilterType = false) }
            }

            EodTransactionsScreenEvent.OnShowMoreFilterTypesClick -> {
                setUiState { copy(showAllTransactionFilterType = true) }
            }

            is EodTransactionsScreenEvent.DateFilterClick -> {
                setUiState {
                    copy(showDatePicker = true, whichDateRange = event.dateRange)
                }
            }

            is EodTransactionsScreenEvent.OnDateSelected -> {
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

            EodTransactionsScreenEvent.OnDismissDatePicker -> {
                setUiState { copy(showDatePicker = false, whichDateRange = null) }
            }

            is EodTransactionsScreenEvent.OnTransactionFilterTypeSelected -> {
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

            is EodTransactionsScreenEvent.OnApplyFilterClick -> {
                val filter = event.transactionFilter
                val isOneTransactionTypeSelected =
                    filter.transactionTypes.filter { it.isSelected }.size == 1

                userPreferencesDataStore.update {
                    copy(eodTransactionFilter = filter)
                }
                loadUiData(
                    filter = TransactionFilterData(
                        dateCreatedFrom = filter.dateFrom?.toString() ?: "",
                        filter.dateTo?.toString() ?: "",
                        if (isOneTransactionTypeSelected) filter.transactionTypes.find { it.isSelected }?.id.toString() else "",
                    ),
                )
            }

            is EodTransactionsScreenEvent.RemoveTransactionTypeFilterItem -> {
                userPreferencesDataStore.update {
                    copy(
                        eodTransactionFilter = eodTransactionFilter.copy(
                            transactionTypes = eodTransactionFilter.transactionTypes.map { type ->
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

            EodTransactionsScreenEvent.OnClearAllFilters -> {
                userPreferencesDataStore.update {
                    copy(eodTransactionFilter = TransactionFilter())
                }
                loadUiData()
            }

            EodTransactionsScreenEvent.OnRefresh -> {
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

            EodTransactionsScreenEvent.RemoveAccountNameItem -> userPreferencesDataStore.update {
                copy(remoteTransactionFilter = remoteTransactionFilter.copy(accountName = ""))
            }

            is EodTransactionsScreenEvent.RemoveCashFlowItem -> userPreferencesDataStore.update {
                copy(remoteTransactionFilter = remoteTransactionFilter.copy(cashFlows = remoteTransactionFilter.cashFlows.map {
                    if (it.title == event.cashFlow.title) {
                        when (it) {
                            is CashFlow.Credit -> it.copy(it.isSelected.not())
                            is CashFlow.Debit -> it.copy(it.isSelected.not())
                        }
                    } else it
                }))
            }

            is EodTransactionsScreenEvent.RemoveDateItem -> userPreferencesDataStore.update {
                copy(
                    remoteTransactionFilter = when (event.whichDate) {
                        DateRange.START_DATE -> remoteTransactionFilter.copy(dateFrom = null)
                        DateRange.END_DATE -> remoteTransactionFilter.copy(dateTo = null)
                    }
                )
            }

            EodTransactionsScreenEvent.RemoveTransactionReferenceItem -> userPreferencesDataStore.update {
                copy(remoteTransactionFilter = remoteTransactionFilter.copy(transactionReference = ""))
            }
        }
    }

    private suspend fun loadUiData(
        filter: TransactionFilterData? = null,
        isTriggeredByRefresh: Boolean = false,
    ) {
        combine(
            flow = getEodTransactionsUseCase.invoke(
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
                val eodTransactionFilter = triple.third.eodTransactionFilter
                setUiState {
                    copy(
                        isTransactionsLoading = triple.first == Resource.Loading,
                        isTransactionFilterTypesLoading = triple.second == Resource.Loading,
                        startDateFilter = eodTransactionFilter.dateFrom,
                        endDateFilter = eodTransactionFilter.dateTo,
                        cashFlows = eodTransactionFilter.cashFlows,
                        transactionReferenceTFV = TextFieldValue(eodTransactionFilter.transactionReference),
                        accountNameTFV = TextFieldValue(eodTransactionFilter.accountName),
                        allTransactionFilterTypes = eodTransactionFilter.transactionTypes,
                        selectedTransactionFilterTypes = eodTransactionFilter.transactionTypes.filter { type -> type.isSelected },
                        isRefreshing = isTriggeredByRefresh
                    )
                }
            }
            if (triple.first is Resource.Ready && triple.second is Resource.Ready) {
                setUiState {
                    val selectedTransactionFilterTypes =
                        triple.third.eodTransactionFilter.transactionTypes.filter { it.isSelected }
                    val allTransactionFilterTypes =
                        (triple.second as Resource.Ready<List<TransactionFilterType>>).data.distinct()
                            .map { type ->
                                selectedTransactionFilterTypes.find { selectedType ->
                                    type.id == selectedType.id
                                } ?: type
                            }
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
                val selectedTypeIDs = userPrefData.eodTransactionFilter.transactionTypes
                    .filter { type: TransactionFilterType -> type.isSelected }
                    .map { type: TransactionFilterType -> type.id }
                if (selectedTypeIDs.isEmpty()) {
                    true
                } else {
                    selectedTypeIDs.any { id: Long -> id == transaction.transactionTypeId }
                }
            }
            .filter { transaction: Transaction ->
                val selectedCashFlows = userPrefData.eodTransactionFilter.cashFlows
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
                    userPrefData.eodTransactionFilter.transactionReference,
                    true,
                )
            }
            .filter { transaction: Transaction ->
                when (transaction) {
                    is Transaction.Eod -> when (transaction) {
                        is Transaction.Eod.BankTransfer -> transaction.beneficiaryAccountName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )

                        is Transaction.Eod.BillPayment -> transaction.paidByAccountName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )

                        is Transaction.Eod.CardPayment -> transaction.cardHolderName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )

                        is Transaction.Eod.CardTransfer -> transaction.beneficiaryName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        ) || transaction.senderName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )

                        is Transaction.Eod.PayWithTransfer -> transaction.receiverName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        ) || transaction.senderAccountName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )
                    }

                    is Transaction.History -> transaction.receiverName.contains(
                        userPrefData.eodTransactionFilter.accountName,
                        true,
                    ) || transaction.senderName.contains(
                        userPrefData.eodTransactionFilter.accountName,
                        true,
                    )
                }
            }
    }
}
