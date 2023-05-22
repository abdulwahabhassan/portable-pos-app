package com.bankly.feature.authentication.ui.recover

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.repository.UserRepository
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class OtpValidationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<OtpValidationUiEvent, OtpValidationState>(OtpValidationState.Initial) {

    override fun handleUiEvents(event: OtpValidationUiEvent) {
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

    private fun validateOtp(otp: String, phoneNumber: String) {
        viewModelScope.launch {
            userRepository.validateOtp(
                body = ValidateOtpRequestBody(otp = otp, phoneNumber = phoneNumber)
            )
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
                            it.message ?: "An unexpected event occurred. We're fixing this!"
                        )
                    }
                }.collect()
        }
    }

    private fun resendOtp(phoneNumber: String) {
        viewModelScope.launch {
            userRepository.forgotPassCode(body = ForgotPassCodeRequestBody(phoneNumber = phoneNumber))
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
                            it.message ?: "An unexpected event occurred. We're fixing this!"
                        )
                    }
                }.collect()
        }
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
