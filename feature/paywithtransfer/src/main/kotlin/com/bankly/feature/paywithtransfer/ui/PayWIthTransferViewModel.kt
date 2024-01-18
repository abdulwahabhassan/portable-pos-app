package com.bankly.feature.paywithtransfer.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.model.data.GetRecentFundingData
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetAgentAccountDetailsUseCase
import com.bankly.core.domain.usecase.GetEodTransactionsUseCase
import com.bankly.core.domain.usecase.GetLocalRecentFundsUseCase
import com.bankly.core.domain.usecase.GetRecentFundUseCase
import com.bankly.core.domain.usecase.GetRemoteRecentFundsUseCase
import com.bankly.core.domain.usecase.InsertRecentFundUseCase
import com.bankly.core.domain.usecase.SendReceiptUseCase
import com.bankly.core.domain.usecase.SyncRecentFundingUseCase
import com.bankly.core.model.data.SyncRecentFundingData
import com.bankly.core.model.entity.RecentFund
import com.bankly.core.model.sealed.onFailure
import com.bankly.core.model.sealed.onLoading
import com.bankly.core.model.sealed.onReady
import com.bankly.core.model.sealed.onSessionExpired
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PayWithTransferViewModel @Inject constructor(
    private val getRemoteRecentFundsUseCase: GetRemoteRecentFundsUseCase,
    private val getLocalRecentFundsUseCase: GetLocalRecentFundsUseCase,
    private val syncRecentFundingUseCase: SyncRecentFundingUseCase,
    private val getRecentFundUseCase: GetRecentFundUseCase,
    private val insertRecentFundUseCase: InsertRecentFundUseCase,
    private val sendReceiptUseCase: SendReceiptUseCase,
    private val getAgentAccountDetailsUseCase: GetAgentAccountDetailsUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) :
    BaseViewModel<PayWithTransferScreenEvent, PayWithTransferScreenState, PayWithTransferScreenOneShotState>(
        PayWithTransferScreenState(),
    ) {

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
                markRecentFundAsSeen(
                    transactionRef = event.recentFund.transactionReference,
                    sessionId = event.recentFund.sessionId
                )
            }

            PayWithTransferScreenEvent.CloseRecentFundSummaryDialog -> {
                setUiState { copy(showRecentFundDialog = false) }
            }

            PayWithTransferScreenEvent.DismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }

            PayWithTransferScreenEvent.LoadUiData -> {
                getAgentAccountDetails()
                getLocalRecentFunds()
                getRemoteRecentFunding()
            }
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
                    Log.d(
                        "debug agent account details",
                        "onReady agent account details: $agentAccountDetails"
                    )
                    setUiState {
                        copy(
                            agentAccountDetails = agentAccountDetails,
                            isAgentAccountDetailsLoading = false
                        )
                    }
                }
                resource.onFailure { message ->
                    Log.d(
                        "debug agent account details",
                        "onFailure agent account details: $message"
                    )
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

    private suspend fun getLocalRecentFunds() {
        getLocalRecentFundsUseCase.invoke().onEach {
            setUiState { copy(recentFunds = it) }
        }.launchIn(viewModelScope)
    }

    private suspend fun getRemoteRecentFunding() {
        getRemoteRecentFundsUseCase.invoke(
            token = userPreferencesDataStore.data().token,
            body = GetRecentFundingData(true, Tools.serialNumber),
        ).onEach { remoteResource ->
            remoteResource.onReady {
                setUiState { copy(isRecentFundsLoading = false) }
                it.forEach { recentFund: RecentFund ->
                    insertRecentFundUseCase.invoke(recentFund)
                }
            }
            remoteResource.onLoading {
                setUiState { copy(isRecentFundsLoading = true) }
            }
            remoteResource.onFailure { message: String ->
                setUiState {
                    copy(
                        isRecentFundsLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = message,
                    )
                }
            }
            remoteResource.onSessionExpired {
                setUiState { copy(isRecentFundsLoading = false) }
                setOneShotState(PayWithTransferScreenOneShotState.OnSessionExpired)
            }
        }.catch {
            it.printStackTrace()
            setUiState {
                copy(
                    isRecentFundsLoading = false,
                    showErrorDialog = true,
                    errorDialogMessage = it.message ?: "Request could not be completed",
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun markRecentFundAsSeen(transactionRef: String, sessionId: String) {
        viewModelScope.launch {
            Log.d("debug", "Transaction Ref to be marked -> $transactionRef")
            val recentFund = getRecentFundUseCase.invoke(transactionRef, sessionId)
            Log.d("debug", "recent fund -> $recentFund")
            if (recentFund != null && !recentFund.seen) {
                Log.d("debug", "Update as synced called")
                //this notifies the server that this transaction has been seen by the user
                syncRecentFundingUseCase.invoke(
                    userPreferencesDataStore.data().token,
                    SyncRecentFundingData(sessionId, Tools.serialNumber)
                )
                //update the app's local database as well
                insertRecentFundUseCase.invoke(recentFund.copy(seen = true))
            }
        }
    }

}
