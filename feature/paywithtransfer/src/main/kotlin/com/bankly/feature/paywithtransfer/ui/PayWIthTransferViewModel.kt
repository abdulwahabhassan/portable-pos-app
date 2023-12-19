package com.bankly.feature.paywithtransfer.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.GetRecentFundingData
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetAgentAccountDetailsUseCase
import com.bankly.core.domain.usecase.GetRecentFundingUseCase
import com.bankly.core.domain.usecase.SendReceiptUseCase
import com.bankly.core.domain.usecase.SyncRecentFundingUseCase
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import com.bankly.core.sealed.onSessionExpired
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PayWithTransferViewModel @Inject constructor(
    private val getRecentFundingUseCase: GetRecentFundingUseCase,
    private val syncRecentFundingUseCase: SyncRecentFundingUseCase,
    private val sendReceiptUseCase: SendReceiptUseCase,
    private val getAgentAccountDetailsUseCase: GetAgentAccountDetailsUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) :
    BaseViewModel<PayWithTransferScreenEvent, PayWithTransferScreenState, PayWithTransferScreenOneShotState>(
        PayWithTransferScreenState(),
    ) {
    init {
        viewModelScope.launch {
            getAgentAccountDetails()
            getRecentFunding()
        }
    }

    private suspend fun getAgentAccountDetails() {
        getAgentAccountDetailsUseCase.invoke(
            userPreferencesDataStore.data().token,
        )
            .onEach { resource ->
                resource.onLoading {
                    setUiState {
                        copy(isAgentAccountDetailsLoading = true)
                    }
                }
                resource.onReady { agentAccountDetails ->
                    Log.d("debug agent account details", "onReady agent account details: $agentAccountDetails")
                    setUiState {
                        copy(agentAccountDetails = agentAccountDetails, isAgentAccountDetailsLoading = false)
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug agent account details", "onFailure agent account details: $message")
                    setUiState {
                        copy(
                            isAgentAccountDetailsLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = message,
                        )
                    }
                }
                resource.onSessionExpired {
                    setOneShotState(PayWithTransferScreenOneShotState.OnSessionExpired)
                }
            }
            .catch {
                Log.d("debug agent account details", "catch agent account details: ${it.message}")
                it.printStackTrace()
                setUiState {
                    copy(
                        isAgentAccountDetailsLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: "Request could not be completed",
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun getRecentFunding() {
        getRecentFundingUseCase.invoke(
            userPreferencesDataStore.data().token,
            GetRecentFundingData(false, Tools.serialNumber),
        )
            .onEach { resource ->
                resource.onLoading {
                    setUiState {
                        copy(isRecentFundsLoading = true)
                    }
                }
                resource.onReady { recentFunds ->
                    setUiState {
                        copy(recentFunds = recentFunds, isRecentFundsLoading = false)
                    }
                }
                resource.onFailure { message ->
                    setUiState {
                        copy(
                            isRecentFundsLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = message,
                        )
                    }
                }
                resource.onSessionExpired {
                    setOneShotState(PayWithTransferScreenOneShotState.OnSessionExpired)
                }
            }
            .catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        isRecentFundsLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: "Request could not be completed",
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override suspend fun handleUiEvents(event: PayWithTransferScreenEvent) {
        when (event) {
            is PayWithTransferScreenEvent.OnAccountDetailsExpandButtonClick -> {
                setUiState { copy(isAccountDetailsExpanded = !event.isExpanded) }
            }

            is PayWithTransferScreenEvent.OnRecentFundSelected -> {
                setUiState {
                    copy(
                        showRecentFundDialog = true,
                        selectedRecentFund = event.recentFund,
                    )
                }
            }

            PayWithTransferScreenEvent.CloseRecentFundSummaryDialog -> {
                setUiState { copy(showRecentFundDialog = false) }
            }

            PayWithTransferScreenEvent.DismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }
        }
    }
}
