package com.bankly.feature.eod.ui.synceod

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetEodInfoUseCase
import com.bankly.core.domain.usecase.SyncEodUseCase
import com.bankly.core.entity.EodInfo
import com.bankly.core.entity.SyncEod
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import com.bankly.core.sealed.onSessionExpired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SyncEodViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val getEodInfoUseCase: GetEodInfoUseCase,
    private val syncEodInfoUseCase: SyncEodUseCase,
) : BaseViewModel<SyncEodScreenEvent, SyncEodScreenState, SyncEodScreenOneShotState>(
    SyncEodScreenState(),
) {
    override suspend fun handleUiEvents(event: SyncEodScreenEvent) {
        when (event) {
            SyncEodScreenEvent.LoadUiData -> {
                fetchEodInfo()
            }

            SyncEodScreenEvent.OnSyncTapEod -> {
                syncEod()
            }

            SyncEodScreenEvent.OnDismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }

            SyncEodScreenEvent.OnDismissSuccessfulEodSyncDialog -> {
                setUiState {
                    copy(
                        showSuccessfulEodSyncDialog = false,
                        successfulEodSyncMessage = "",
                    )
                }
            }
        }
    }

    private suspend fun syncEod() {
        syncEodInfoUseCase.invoke(userPreferencesDataStore.data().token, emptyList())
            .onEach { resource: Resource<SyncEod> ->
                resource.onLoading {
                    setUiState { copy(isEodSyncLoading = true) }
                }
                resource.onReady { syncEod ->
                    setUiState {
                        copy(
                            isEodSyncLoading = false,
                            showSuccessfulEodSyncDialog = true,
                            successfulEodSyncMessage = syncEod.responseMessage,
                        )
                    }
                    fetchEodInfo()
                }
                resource.onFailure { message: String ->
                    setUiState {
                        copy(
                            isEodSyncLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = message,
                        )
                    }
                }
                resource.onSessionExpired {
                    setOneShotState(SyncEodScreenOneShotState.OnSessionExpired)
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        isEodSyncLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: "",
                    )
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun fetchEodInfo() {
        getEodInfoUseCase.invoke(userPreferencesDataStore.data().token)
            .onEach { resource: Resource<EodInfo> ->
                resource.onLoading {
                    setUiState { copy(isEodInfoLoading = true) }
                }
                resource.onReady { eodInfo: EodInfo ->
                    setUiState {
                        copy(
                            isEodInfoLoading = false,
                            settledAmount = eodInfo.settled,
                        )
                    }
                }
                resource.onFailure { message: String ->
                    setUiState {
                        copy(
                            isEodInfoLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = message,
                        )
                    }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        isEodInfoLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: "",
                    )
                }
            }.launchIn(viewModelScope)
    }
}
