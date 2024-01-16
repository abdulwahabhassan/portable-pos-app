package com.bankly.feature.authentication.ui.setnewpasscode

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.util.Validator.doPassCodesMatch
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.model.data.ResetPassCodeData
import com.bankly.core.domain.usecase.ResetPassCodeUseCase
import com.bankly.core.model.entity.Message
import com.bankly.core.model.sealed.State
import com.bankly.core.model.sealed.onFailure
import com.bankly.core.model.sealed.onLoading
import com.bankly.core.model.sealed.onReady
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SetNewPassCodeViewModel @Inject constructor(
    private val resetPassCodeUseCase: ResetPassCodeUseCase,
) : BaseViewModel<SetNewPassCodeScreenEvent, SetNewPassCodeScreenState, SetNewPassCodeScreenOneShotState>(SetNewPassCodeScreenState()) {

    override suspend fun handleUiEvents(event: SetNewPassCodeScreenEvent) {
        when (event) {
            is SetNewPassCodeScreenEvent.OnDoneClick -> {
                resetPassCode(
                    phoneNumber = event.phoneNumber,
                    passCode = event.passCode,
                    confirmPassCode = event.confirmPassCode,
                    otp = event.otp,
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
                        passCodeTFV.text.trim(),
                    )
                    copy(
                        confirmPassCodeTFV = event.confirmPassCodeTFV,
                        isConfirmPassCodeError = isEmpty || isAMatch.not(),
                        confirmPassCodeFeedBack = if (isEmpty) {
                            "Please enter passcode"
                        } else if (isAMatch.not()) {
                            "Passcode does not match"
                        } else {
                            ""
                        },
                    )
                }
            }

            is SetNewPassCodeScreenEvent.OnEnterPasscode -> {
                setUiState {
                    val isEmpty = event.passCodeTFV.text.trim().isEmpty()
                    copy(
                        passCodeTFV = event.passCodeTFV,
                        isPassCodeError = isEmpty,
                        passCodeFeedBack = if (isEmpty) "Please enter passcode" else "",
                    )
                }
            }

            SetNewPassCodeScreenEvent.OnDismissWarningDialog -> {
                setUiState { copy(shouldShowWarningDialog = false) }
            }

            SetNewPassCodeScreenEvent.OnExit -> {
                setUiState { copy(setNewPassCodeState = State.Initial) }
            }
        }
    }

    private suspend fun resetPassCode(
        passCode: String,
        confirmPassCode: String,
        phoneNumber: String,
        otp: String,
    ) {
        resetPassCodeUseCase(
            body = com.bankly.core.model.data.ResetPassCodeData(
                username = phoneNumber,
                password = passCode,
                confirmPassword = confirmPassCode,
                code = otp,
            ),
        )
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(setNewPassCodeState = State.Loading) }
                }
                resource.onReady { message: com.bankly.core.model.entity.Message ->
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
                            it.localizedMessage ?: "An unexpected event occurred!",
                        ),
                    )
                }
            }.launchIn(viewModelScope)
    }
}
