package com.bankly.feature.dashboard.ui.transactions

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetTransactionsUseCase
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TransactionsViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val getTransactionsUseCase: GetTransactionsUseCase,
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

            is TransactionsScreenEvent.OnTransactionSelected -> {
                setOneShotState(TransactionsScreenOneShotState.GoToTransactionDetailsScreen(event.transaction.toTransactionReceipt()))
            }

            TransactionsScreenEvent.FetchTransactions -> { getTransactions() }
        }
    }

    private suspend fun getTransactions() {
        getTransactionsUseCase.invoke(
            token = userPreferencesDataStore.data().token,
            minimum = 1,
            maximum = 100,
            filter = "{}"
        ).onEach { resource ->
            resource.onLoading {
                setUiState { copy(isTransactionsLoading = true) }
                setOneShotState(TransactionsScreenOneShotState.UpdateLoadingIndicator(isLoading = true))
            }
            resource.onReady { transactions ->
                setUiState { copy(isTransactionsLoading = false, transactions = transactions) }
                setOneShotState(TransactionsScreenOneShotState.UpdateLoadingIndicator(isLoading = false))
            }
            resource.onFailure { message ->
                setUiState {
                    copy(
                        isTransactionsLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = message
                    )
                }
                setOneShotState(TransactionsScreenOneShotState.UpdateLoadingIndicator(isLoading = false))
            }
        }.catch {
            it.printStackTrace()
            setUiState {
                copy(
                    isTransactionsLoading = false,
                    showErrorDialog = true,
                    errorDialogMessage = it.message ?: ""
                )
            }
            setOneShotState(TransactionsScreenOneShotState.UpdateLoadingIndicator(isLoading = false))
        }
            .launchIn(viewModelScope)
    }
}
