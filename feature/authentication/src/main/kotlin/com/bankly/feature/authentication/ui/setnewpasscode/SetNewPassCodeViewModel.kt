package com.bankly.feature.authentication.ui.setnewpasscode

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.State
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.util.Validator.doPassCodesMatch
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.domain.usecase.ResetPassCodeUseCase
import com.bankly.core.model.Message
import com.bankly.core.model.ResetPassCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class SetNewPassCodeViewModel @Inject constructor(
    private val resetPassCodeUseCase: ResetPassCodeUseCase
) : BaseViewModel<SetNewPassCodeScreenEvent, SetNewPassCodeScreenState>(SetNewPassCodeScreenState()) {

    override suspend fun handleUiEvents(event: SetNewPassCodeScreenEvent) {
        when (event) {
            is SetNewPassCodeScreenEvent.OnDoneClick -> {
                resetPassCode(
                    phoneNumber = event.phoneNumber,
                    passCode = event.passCode,
                    confirmPassCode = event.confirmPassCode,
                    otp = event.otp
                )
            }

            SetNewPassCodeScreenEvent.OnBackPress -> {
                setUiState { copy(shouldShowWarningDialog = true) }
            }

            is SetNewPassCodeScreenEvent.OnEnterConfirmPasscode -> {
                setUiState {
                    val isEmpty = event.confirmPassCodeTFV.text.trim().isEmpty()
                    val isAMatch = doPassCodesMatch(
                        event.confirmPassCodeTFV.text.trim(),
                        passCodeTFV.text.trim()
                    )
                    copy(
                        confirmPassCodeTFV = event.confirmPassCodeTFV,
                        isConfirmPassCodeError = isEmpty || isAMatch.not(),
                        confirmPassCodeFeedBack = if (isEmpty) "Please enter passcode"
                        else if (isAMatch.not()) "Passcode does not match"
                        else ""
                    )
                }
            }

            is SetNewPassCodeScreenEvent.OnEnterPasscode -> {
                setUiState {
                    val isEmpty = event.passCodeTFV.text.trim().isEmpty()
                    copy(
                        passCodeTFV = event.passCodeTFV,
                        isPassCodeError = isEmpty,
                        passCodeFeedBack = if (isEmpty) "Please enter passcode" else ""
                    )
                }
            }

            SetNewPassCodeScreenEvent.OnDismissWarningDialog -> {
                setUiState { copy(shouldShowWarningDialog = false) }
            }
        }
    }

    private suspend fun resetPassCode(
        passCode: String, confirmPassCode: String, phoneNumber: String, otp: String
    ) {
        resetPassCodeUseCase(
            body = ResetPassCode(
                username = phoneNumber,
                password = passCode,
                confirmPassword = confirmPassCode,
                code = otp
            )
        )
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(setNewPassCodeState = State.Loading) }
                }
                resource.onReady { message: Message ->
                    setUiState { copy(setNewPassCodeState = State.Success(message)) }
                }
                resource.onFailure { message: String ->
                    setUiState { copy(setNewPassCodeState = State.Error(message)) }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        setNewPassCodeState = State.Error(
                            it.localizedMessage ?: "An unexpected event occurred!"
                        )
                    )
                }
            }.launchIn(viewModelScope)
    }

}