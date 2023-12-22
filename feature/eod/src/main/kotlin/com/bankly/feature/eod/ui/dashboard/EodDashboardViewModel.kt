package com.bankly.feature.eod.ui.dashboard

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.TransactionFilterData
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetEodTransactionsUseCase
import com.bankly.core.entity.Transaction
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
internal class EodDashboardViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val eodTransactionsUseCase: GetEodTransactionsUseCase,
) : BaseViewModel<EodDashboardScreenEvent, EodDashboardScreenState, EodDashboardScreenOneShotState>(
    EodDashboardScreenState(),
) {
    override suspend fun handleUiEvents(event: EodDashboardScreenEvent) {
        when (event) {
            EodDashboardScreenEvent.LoadUiData -> {
                val userPrefData = userPreferencesDataStore.data()
                eodTransactionsUseCase.invoke(
                    filter = TransactionFilterData(
                        dateCreatedFrom = LocalDate.now().toString(),
                        dateCreatedTo = LocalDate.now().toString(),
                        transactionType = if (userPrefData.eodTransactionFilter.transactionTypes.size == 1) {
                            userPrefData.eodTransactionFilter.transactionTypes[0].id.toString()
                        } else {
                            ""
                        },
                    )
                ).onEach { resource: Resource<List<Transaction>> ->
                    resource.onReady { transactions: List<Transaction> ->
                        val totalSuccessful = transactions.filter {
                            it.toTransactionReceipt().isSuccessfulTransaction()
                        }.sumOf { it.transactionAmount }
                        val totalFailed = transactions.filter {
                            !it.toTransactionReceipt().isSuccessfulTransaction()
                        }.sumOf { it.transactionAmount }
                        val total = transactions.sumOf { it.transactionAmount }
                        setUiState {
                            copy(
                                totalTransactionAmount = total,
                                totalSuccessfulTransactionAmount = totalSuccessful,
                                totalFailedTransactionAmount = totalFailed
                            )
                        }
                    }
                    resource.onLoading {
                        setUiState { copy(isTransactionSummaryLoading = true) }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
