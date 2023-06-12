package com.bankly.feature.authentication.ui.passcode

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.util.Validator
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.domain.usecase.ResetPassCodeUseCase
import com.bankly.core.model.ResetPassCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class SetNewPassCodeViewModel @Inject constructor(
    private val resetPassCodeUseCase: ResetPassCodeUseCase
) : BaseViewModel<SetNewPassCodeUiEvent, SetNewPassCodeState>(SetNewPassCodeState.Initial) {

    override fun handleUiEvents(event: SetNewPassCodeUiEvent) {
        when (event) {
            is SetNewPassCodeUiEvent.SetNewPassCode -> {
                if (Validator.validatePassCodes(event.passCode, event.confirmPassCode)) {
                    resetPassCode(
                        phoneNumber = event.phoneNumber,
                        passCode = event.passCode,
                        confirmPassCode = event.confirmPassCode,
                        otp = event.otp
                    )
                } else {
                    setUiState { SetNewPassCodeState.Error("Please re-confirm your passcode") }
                }
            }

            SetNewPassCodeUiEvent.ResetState -> {
                setUiState { SetNewPassCodeState.Initial }
            }
        }
    }

    private fun resetPassCode(
        passCode: String, confirmPassCode: String, phoneNumber: String, otp: String
    ) {
        viewModelScope.launch {
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
                        setUiState { SetNewPassCodeState.Loading }
                    }
                    resource.onReady { message ->
                        setUiState { SetNewPassCodeState.Success(message.message) }
                    }
                    resource.onFailure { message ->
                        setUiState { SetNewPassCodeState.Error(message) }
                    }
                }.catch {
                    it.printStackTrace()
                    setUiState {
                        SetNewPassCodeState.Error(
                            it.message ?: "An unexpected event occurred. We're fixing this!"
                        )
                    }
                }.collect()
        }
    }

}

sealed interface SetNewPassCodeState {
    object Initial : SetNewPassCodeState
    object Loading : SetNewPassCodeState
    data class Success(val message: String) : SetNewPassCodeState
    data class Error(val errorMessage: String) : SetNewPassCodeState
}

sealed interface SetNewPassCodeUiEvent {
    data class SetNewPassCode(
        val passCode: String,
        val confirmPassCode: String,
        val phoneNumber: String,
        val otp: String
    ) : SetNewPassCodeUiEvent

    object ResetState : SetNewPassCodeUiEvent
}
