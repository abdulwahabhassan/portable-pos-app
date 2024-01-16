package com.bankly.feature.networkchecker.ui

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBankNetworksUseCase
import com.bankly.core.model.entity.BankNetwork
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.onFailure
import com.bankly.core.model.sealed.onLoading
import com.bankly.core.model.sealed.onReady
import com.bankly.core.model.sealed.onSessionExpired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class NetworkCheckerViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val getNetworkCheckerUseCase: GetBankNetworksUseCase,
) :
    BaseViewModel<NetworkCheckerScreenEvent, NetworkCheckerScreenState, NetworkCheckerScreenOneShotState>(
        NetworkCheckerScreenState(),
    ) {

    override suspend fun handleUiEvents(event: NetworkCheckerScreenEvent) {
        when (event) {
            NetworkCheckerScreenEvent.DismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }

            is NetworkCheckerScreenEvent.OnTabSelected -> {
                setUiState { copy(selectedTab = event.tab) }
            }

            NetworkCheckerScreenEvent.LoadUiData -> {
                getNetworkCheckerUseCase.invoke(userPreferencesDataStore.data().token)
                    .onEach { resource: Resource<List<com.bankly.core.model.entity.BankNetwork>> ->
                        resource.onLoading {
                            setUiState { copy(isBankListLoading = true) }
                        }
                        resource.onFailure { errorMessage: String ->
                            setUiState {
                                copy(
                                    isBankListLoading = false,
                                    showErrorDialog = true,
                                    errorDialogMessage = errorMessage,
                                )
                            }
                        }
                        resource.onReady { bankNetworks: List<com.bankly.core.model.entity.BankNetwork> ->
                            setUiState {
                                copy(
                                    isBankListLoading = false,
                                    bankNetworks = bankNetworks,
                                )
                            }
                        }
                        resource.onSessionExpired {
                            setOneShotState(NetworkCheckerScreenOneShotState.OnSessionExpired)
                        }
                    }.catch {
                        it.printStackTrace()
                        setUiState {
                            copy(
                                isBankListLoading = false,
                                showErrorDialog = true,
                                errorDialogMessage = it.message ?: "",
                            )
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }
}
