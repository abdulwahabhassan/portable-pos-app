package com.bankly.feature.authentication.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.ForgotTerminalAccessPinUseCase
import com.bankly.core.domain.usecase.GetTokenUseCase
import com.bankly.core.model.sealed.onFailure
import com.bankly.core.model.sealed.onLoading
import com.bankly.core.model.sealed.onReady
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

private const val THREE = "3"
private const val FOUR = "4"
private const val INVALID_PASSCODE = "invalid passcode"
private const val PASSCODE = "Passcode"
private const val ACCESS_PIN = "Access PIN"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val forgotTerminalAccessPinUseCase: ForgotTerminalAccessPinUseCase,
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

            LoginScreenEvent.ClearAccessPinInputField -> {
                setUiState { copy(passCode = List(6) { "" }) }
            }

            LoginScreenEvent.OnResetAccessPinClick -> {
                resetAccessPin()
            }

            LoginScreenEvent.RestoreLoginMode -> {
                setUiState { copy(isResetAccessPin = false) }
            }

            LoginScreenEvent.ShowExitResetPinWarningDialog -> {
                setUiState { copy(showExitResetPinDialog = true) }
            }

            LoginScreenEvent.OnDismissExitResetPinWarningDialog -> {
                setUiState { copy(showExitResetPinDialog = false) }
            }

            LoginScreenEvent.OnResendAccessCodeClick -> {
                resetAccessPin()
            }
        }
    }

    private suspend fun performLogin(passCode: String) {
        loginTokenUseCase(
//            userName = "P260300061091",
            userName = Tools.serialNumber,
            password = passCode,
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
                            setOneShotState(LoginScreenOneShotState.OnSetUpAccessPin(defaultPin = passCode))
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
                                    errorDialogMessage = failureMessage.replace(
                                        oldValue = PASSCODE,
                                        newValue = ACCESS_PIN,
                                        ignoreCase = true,
                                    ),
                                    isPassCodeError = failureMessage.contains(
                                        other = INVALID_PASSCODE,
                                        ignoreCase = true,
                                    ),
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
                        ?: "",
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun startResendCodeTimer() {
        viewModelScope.launch {
            while (uiState.value.ticks > 0) {
                delay(1.seconds)
                setUiState {
                    copy(ticks = uiState.value.ticks - 1)
                }
            }
        }
    }

    private suspend fun resetAccessPin() {
        forgotTerminalAccessPinUseCase(
            body = com.bankly.core.model.data.ForgotTerminalAccessPinData(
                serialNumber = Tools.serialNumber
            )
        )
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(isLoading = true) }
                }
                resource.onReady { _ ->
                    setUiState { copy(isLoading = false, isResetAccessPin = true, ticks = 60) }
                    startResendCodeTimer()
                }
                resource.onFailure { message ->
                    setUiState {
                        copy(
                            showErrorDialog = true,
                            errorDialogMessage = message,
                            isLoading = false,
                        )
                    }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: "",
                        isLoading = false,
                    )
                }
            }.launchIn(viewModelScope)
    }
}
