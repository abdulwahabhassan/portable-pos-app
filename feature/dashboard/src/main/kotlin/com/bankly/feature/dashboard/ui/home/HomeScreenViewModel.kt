package com.bankly.feature.dashboard.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetWalletUseCase
import com.bankly.core.model.UserWallet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : BaseViewModel<HomeUiEvent, HomeUiState>(HomeUiState()) {

    override suspend fun handleUiEvents(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ToggleWalletBalanceVisibilityEvent -> toggleWalletBalanceVisibility(event.shouldShowWalletBalance)
            HomeUiEvent.DismissErrorDialog -> dismissErrorDialog()
        }
    }

    init {
        viewModelScope.launch { getWallet() }
    }

    private suspend fun getWallet() {
        getWalletUseCase(token = userPreferencesDataStore.data().token)
            .onEach { resource ->
                resource.onLoading {
                    Log.d("debug", "loading wallet ..")
                    setUiState { copy(shouldShowLoadingIcon = true) }
                }
                resource.onReady { userWallet: UserWallet ->
                    Log.d("debug", "ready wallet .. $resource")
                    val shouldShowWalletBalance =
                        userPreferencesDataStore.data().shouldShowWalletBalance
                    setUiState {
                        copy(
                            accountBalance = userWallet.accountBalance,
                            bankName = userWallet.bankName,
                            accountNumber = userWallet.accountNumber,
                            accountName = userWallet.accountName,
                            shouldShowWalletBalance = shouldShowWalletBalance,
                            shouldShowVisibilityIcon = true,
                            shouldShowLoadingIcon = false
                        )
                    }

                }
                resource.onFailure { message ->
                    Log.d("debug", "failure wallet .. $resource")
                    setUiState {
                        copy(message = message, shouldShowErrorDialog = true)
                    }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        shouldShowErrorDialog = true,
                        message = it.message ?: "An expected event occurred!"
                    )
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun toggleWalletBalanceVisibility(isVisible: Boolean) {
        userPreferencesDataStore.update { copy(shouldShowWalletBalance = isVisible) }
        setUiState { copy(shouldShowWalletBalance = isVisible) }
    }

    private fun dismissErrorDialog() {
        setUiState { copy(shouldShowErrorDialog = false) }
    }
}

data class HomeUiState(
    val accountBalance: Double = 0.00,
    val bankName: String = "",
    val accountNumber: String = "",
    val accountName: String = "",
    val shouldShowWalletBalance: Boolean = false,
    val message: String = "",
    val shouldShowErrorDialog: Boolean = false,
    val shouldShowLoadingIcon: Boolean = true,
    val shouldShowVisibilityIcon: Boolean = false
)

sealed interface HomeUiEvent {
    data class ToggleWalletBalanceVisibilityEvent(val shouldShowWalletBalance: Boolean) :
        HomeUiEvent

    object DismissErrorDialog : HomeUiEvent
}
