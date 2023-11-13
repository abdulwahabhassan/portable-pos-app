package com.bankly.feature.authentication.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetTokenUseCase
import com.bankly.core.sealed.State
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val THREE = "3"
private const val FOUR = "4"
private const val INVALID_PASSCODE = "invalid passcode"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginTokenUseCase: GetTokenUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<LoginScreenEvent, LoginScreenState, LoginScreenOneShotState>(LoginScreenState()) {

    override suspend fun handleUiEvents(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnLoginClick -> {
                performLogin(passCode = event.passCode)
            }

            is LoginScreenEvent.OnEnterPassCode -> {
                setUiState { copy(passCode = event.passCode, isPassCodeError = false) }
            }

            LoginScreenEvent.OnDismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }
        }
    }

    private suspend fun performLogin(passCode: String) {
        loginTokenUseCase(
            userName = userPreferencesDataStore.data().terminalSerialNumber,
            password = passCode
        )
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(isLoading = true) }
                }
                resource.onReady { tokenObj ->
                    userPreferencesDataStore.update {
                        copy(
                            token = buildString {
                                append(tokenObj.tokenType)
                                append(" ")
                                append(tokenObj.token)
                            },
                        )
                    }
                    setUiState { copy(isLoading = false) }
                    setOneShotState(LoginScreenOneShotState.OnLoginSuccess)
                }
                resource.onFailure { failureMessage ->
                    when (failureMessage) {
                        THREE -> {
                            setUiState { copy(isLoading = false) }
                            setOneShotState(LoginScreenOneShotState.OnSetUpAccessPin)
                        }

                        FOUR -> {
                            setUiState { copy(isLoading = false) }
                            setOneShotState(LoginScreenOneShotState.OnTerminalUnAssigned)
                        }

                        else -> {
                            setUiState {
                                copy(
                                    isLoading = false,
                                    showErrorDialog = true,
                                    errorDialogMessage = failureMessage,
                                    isPassCodeError = failureMessage.contains(
                                        other = INVALID_PASSCODE,
                                        ignoreCase = true
                                    )
                                )
                            }
                        }
                    }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        isLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = it.localizedMessage ?: it.message
                        ?: ""
                    )
                }
            }.launchIn(viewModelScope)
    }
}
