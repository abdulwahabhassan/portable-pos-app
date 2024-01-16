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
                    filter = com.bankly.core.model.data.TransactionFilterData(
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
                                    is com.bankly.core.model.entity.CashFlow.Credit -> filter.copy(state = filter.state.not())
                                    is com.bankly.core.model.entity.CashFlow.Debit -> filter.copy(state = filter.state.not())
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
                    filter = com.bankly.core.model.data.TransactionFilterData(
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
        }
    }

    private suspend fun loadUiData(filter: com.bankly.core.model.data.TransactionFilterData? = null) {
        combine(
            flow = getEodTransactionsUseCase.invoke(
                filter = filter ?: com.bankly.core.model.data.TransactionFilterData(),
            ),
            flow2 = getTransactionFilterTypesUseCase.invoke(
                token = userPreferencesDataStore.data().token,
            ),
            flow3 = userPreferencesDataStore.flow(),
        ) { transactions, transactionFilterTypes, userPreferences ->
            Triple(transactions, transactionFilterTypes, userPreferences)
        }.onEach { triple: Triple<Resource<List<com.bankly.core.model.entity.Transaction>>, Resource<List<TransactionFilterType>>, UserPreferences> ->
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
                            transactions = (triple.first as Resource.Ready<List<com.bankly.core.model.entity.Transaction>>).data,
                            userPrefData = triple.third,
                        ),
                    )
                }
            }

            triple.first.onFailure { message: String ->
                setUiState {
                    copy(
                        isTransactionsLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = message,
                    )
                }
            }

            triple.second.onFailure { message: String ->
                setUiState {
                    copy(
                        isTransactionFilterTypesLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = message,
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
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun filterTransactions(
        transactions: List<com.bankly.core.model.entity.Transaction>,
        userPrefData: UserPreferences,
    ): List<com.bankly.core.model.entity.Transaction> {
        return transactions
            .filter { transaction: com.bankly.core.model.entity.Transaction ->
                val selectedTypeIDs = userPrefData.eodTransactionFilter.transactionTypes
                    .filter { type: TransactionFilterType -> type.isSelected }
                    .map { type: TransactionFilterType -> type.id }
                if (selectedTypeIDs.isEmpty()) {
                    true
                } else {
                    selectedTypeIDs.any { id: Long -> id == transaction.transactionTypeId }
                }
            }
            .filter { transaction: com.bankly.core.model.entity.Transaction ->
                val selectedCashFlows = userPrefData.eodTransactionFilter.cashFlows
                    .filter { cashFlow: com.bankly.core.model.entity.CashFlow -> cashFlow.isSelected }
                if (selectedCashFlows.isEmpty()) {
                    true
                } else {
                    selectedCashFlows.any { cashFlow: com.bankly.core.model.entity.CashFlow ->
                        when (cashFlow) {
                            is com.bankly.core.model.entity.CashFlow.Credit -> transaction.isCreditTransaction
                            is com.bankly.core.model.entity.CashFlow.Debit -> transaction.isDebitTransaction
                        }
                    }
                }
            }
            .filter { transaction: com.bankly.core.model.entity.Transaction ->
                transaction.transactionReference.contains(
                    userPrefData.eodTransactionFilter.transactionReference,
                    true,
                )
            }
            .filter { transaction: com.bankly.core.model.entity.Transaction ->
                when (transaction) {
                    is com.bankly.core.model.entity.Transaction.Eod -> when (transaction) {
                        is com.bankly.core.model.entity.Transaction.Eod.BankTransfer -> transaction.beneficiaryAccountName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )

                        is com.bankly.core.model.entity.Transaction.Eod.BillPayment -> transaction.paidByAccountName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )

                        is com.bankly.core.model.entity.Transaction.Eod.CardPayment -> transaction.cardHolderName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )

                        is com.bankly.core.model.entity.Transaction.Eod.CardTransfer -> transaction.beneficiaryName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        ) || transaction.senderName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )

                        is com.bankly.core.model.entity.Transaction.Eod.PayWithTransfer -> transaction.receiverName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        ) || transaction.senderAccountName.contains(
                            userPrefData.eodTransactionFilter.accountName,
                            true,
                        )
                    }

                    is com.bankly.core.model.entity.Transaction.History -> transaction.receiverName.contains(
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
