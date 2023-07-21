package com.bankly.feature.authentication.ui.otp

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.domain.usecase.ForgotPassCodeUseCase
import com.bankly.core.domain.usecase.ValidateOtpUseCase
import com.bankly.core.model.ForgotPassCode
import com.bankly.core.model.ValidateOtp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class OtpValidationViewModel @Inject constructor(
    private val validateOtpUseCase: ValidateOtpUseCase,
    private val forgotPassCodeUseCase: ForgotPassCodeUseCase
) : BaseViewModel<OtpValidationUiEvent, OtpValidationState>(OtpValidationState.Initial) {

    override suspend fun handleUiEvents(event: OtpValidationUiEvent) {
        when (event) {
            is OtpValidationUiEvent.ValidateOtp -> {
                validateOtp(otp = event.otp, phoneNumber = event.phoneNumber)
            }

            OtpValidationUiEvent.ResetState -> {
                setUiState { OtpValidationState.Initial }
            }

            is OtpValidationUiEvent.ResendOtp -> {
                resendOtp(event.phoneNumber)
            }
        }
    }

    private suspend fun validateOtp(otp: String, phoneNumber: String) {
        validateOtpUseCase(body = ValidateOtp(otp = otp, phoneNumber = phoneNumber))
            .onEach { resource ->
                resource.onLoading {
                    setUiState { OtpValidationState.Loading }
                }
                resource.onReady {
                    setUiState { OtpValidationState.OtpValidationSuccess }
                }
                resource.onFailure { message ->
                    setUiState { OtpValidationState.Error(message) }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    OtpValidationState.Error(
                        it.message ?: "An unexpected event occurred!"
                    )
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun resendOtp(phoneNumber: String) {
        forgotPassCodeUseCase(body = ForgotPassCode(phoneNumber = phoneNumber))
            .onEach { resource ->
                resource.onLoading {
                    setUiState { OtpValidationState.Loading }
                }
                resource.onReady {
                    setUiState { OtpValidationState.ResendOtpSuccess }
                }
                resource.onFailure { message ->
                    setUiState { OtpValidationState.Error(message) }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    OtpValidationState.Error(
                        it.message ?: "An unexpected event occurred!"
                    )
                }
            }.launchIn(viewModelScope)
    }

}

sealed interface OtpValidationState {
    object Initial : OtpValidationState
    object Loading : OtpValidationState
    object OtpValidationSuccess : OtpValidationState
    object ResendOtpSuccess : OtpValidationState
    data class Error(val errorMessage: String) : OtpValidationState
}

sealed interface OtpValidationUiEvent {
    data class ValidateOtp(val otp: String, val phoneNumber: String) : OtpValidationUiEvent
    object ResetState : OtpValidationUiEvent
    data class ResendOtp(val phoneNumber: String) : OtpValidationUiEvent
}
