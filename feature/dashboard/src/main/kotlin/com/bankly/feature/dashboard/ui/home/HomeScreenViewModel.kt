package com.bankly.feature.dashboard.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetWalletUseCase
import com.bankly.core.model.entity.UserWallet
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
internal class HomeScreenViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<HomeScreenEvent, HomeScreenState, HomeScreenOneShotState>(HomeScreenState()) {

//    init {
//        viewModelScope.launch {
//            Tools.serialNumber = "P260300061091"
//            Tools.terminalId = "2035144J"
//        }
//    }

    override suspend fun handleUiEvents(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ToggleWalletBalanceVisibility -> toggleWalletBalanceVisibility(event.shouldShowWalletBalance)
            HomeScreenEvent.OnDismissErrorDialog -> dismissErrorDialog()
            HomeScreenEvent.FetchWalletBalance -> {
                getWallet()
            }

            is HomeScreenEvent.OnFeatureCardClick -> {
                val feature = userPreferencesDataStore.data().featureToggleList.find {
                    it.title == event.feature.title
                }
                if (event.feature is com.bankly.core.model.entity.Feature.Settings || feature?.isEnabled == true) {
                    setOneShotState(HomeScreenOneShotState.GoToFeature(event.feature))
                } else {
                    setUiState { copy(showFeatureAccessDeniedDialog = true) }
                }
            }

            HomeScreenEvent.OnDismissFeatureAccessDeniedDialog -> {
                setUiState { copy(showFeatureAccessDeniedDialog = false) }
            }

            HomeScreenEvent.OnRefresh -> {
                getWallet(isTriggeredByRefresh = true)
            }
        }
    }

    private suspend fun getWallet(
        isTriggeredByRefresh: Boolean = false,
    ) {
        getWalletUseCase(token = userPreferencesDataStore.data().token)
            .onEach { resource ->
                resource.onLoading {
                    Log.d("debug", "loading wallet ..")
                    setUiState {
                        copy(
                            isWalletBalanceLoading = true,
                            isRefreshingWalletBalance = isTriggeredByRefresh
                        )
                    }
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
                            isWalletBalanceLoading = false,
                            isRefreshingWalletBalance = false
                        )
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug", "failure wallet .. $resource")
                    setUiState {
                        copy(
                            message = message,
                            shouldShowErrorDialog = true,
                            isWalletBalanceLoading = false,
                            isRefreshingWalletBalance = false
                        )
                    }
                }
                resource.onSessionExpired {
                    setOneShotState(HomeScreenOneShotState.OnSessionExpired)
                    setUiState {
                        copy(isWalletBalanceLoading = false, isRefreshingWalletBalance = false)
                    }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        shouldShowErrorDialog = true,
                        message = it.message ?: "An expected event occurred!",
                        isWalletBalanceLoading = false,
                        isRefreshingWalletBalance = false
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
