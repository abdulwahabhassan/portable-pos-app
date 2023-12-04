package com.bankly.feature.authentication.ui.confirmpin

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.ChangePassCodeData
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.ChangePassCodeUseCase
import com.bankly.core.entity.User
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import com.bankly.core.sealed.onSessionExpired
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val PASSCODE = "Passcode"
private const val ACCESS_PIN = "Access PIN"

@HiltViewModel
class ConfirmPinViewModel @Inject constructor(
    private val changePassCodeUseCase: ChangePassCodeUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<ConfirmPinScreenEvent, ConfirmPinScreenState, ConfirmPinScreenOneShotState>(
    ConfirmPinScreenState()
) {

    override suspend fun handleUiEvents(event: ConfirmPinScreenEvent) {
        when (event) {
            is ConfirmPinScreenEvent.OnEnterConfirmPin -> {
                Log.d("debug confirmPin", event.confirmPin.joinToString(""))
                Log.d("debug newPin", event.newPin)
                val confirmPin = event.confirmPin.joinToString("")
                val isEqual = event.newPin.take(confirmPin.length) == confirmPin
                setUiState {
                    copy(
                        confirmPin = event.confirmPin,
                        isConfirmPinError = isEqual.not(),
                        pinErrorMessage = "PIN Mismatch!"
                    )
                }
            }

            ConfirmPinScreenEvent.OnDismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }

            is ConfirmPinScreenEvent.OnDoneClick -> {
                changeAccessPin(event.defaultPin, event.newPin, event.confirmPin.joinToString(""))
            }

            is ConfirmPinScreenEvent.OnSetPins -> {
                setUiState { copy(defaultPin = event.defaultPin, newPin = event.newPin) }
            }

        }
    }

    private suspend fun changeAccessPin(
        defaultPin: String,
        newPin: String,
        confirmPin: String
    ) {
        changePassCodeUseCase(
            body = ChangePassCodeData(
                serialNumber = Tools.serialNumber,
                oldPasscode = defaultPin,
                newPasscode = newPin,
                confirmPasscode = confirmPin
            )
        )
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(isLoading = true) }
                }
                resource.onReady { user: User ->
                    setUiState { copy(isLoading = false) }
                    setOneShotState(
                        ConfirmPinScreenOneShotState.OnSetPinSuccess(
                            message = user.message.replace(
                                PASSCODE,
                                ACCESS_PIN,
                                true
                            )
                        )
                    )
                }
                resource.onFailure { failureMessage ->
                    setUiState {
                        copy(
                            isLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = failureMessage,
                        )
                    }
                }
                resource.onSessionExpired {
                    setOneShotState(ConfirmPinScreenOneShotState.OnSessionExpired)
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
