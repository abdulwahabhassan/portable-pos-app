package com.bankly.feature.authentication.ui.validateotp

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.ForgotPassCodeData
import com.bankly.core.data.ValidateOtpData
import com.bankly.core.domain.usecase.ForgotPassCodeUseCase
import com.bankly.core.domain.usecase.ValidateOtpUseCase
import com.bankly.core.entity.Status
import com.bankly.core.sealed.State
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class OtpValidationViewModel @Inject constructor(
    private val validateOtpUseCase: ValidateOtpUseCase,
    private val forgotPassCodeUseCase: ForgotPassCodeUseCase,
) : BaseViewModel<OtpValidationScreenEvent, OtpValidationScreenState, OtpValidationScreenOneShotState>(
    OtpValidationScreenState(),
) {

    override suspend fun handleUiEvents(event: OtpValidationScreenEvent) {
        when (event) {
            is OtpValidationScreenEvent.OnDoneClick -> {
                validateOtp(otp = event.otp, phoneNumber = event.phoneNumber)
            }

            is OtpValidationScreenEvent.OnResendOtpClick -> {
                resendOtp(event.phoneNumber)
            }

            is OtpValidationScreenEvent.OnEnterOtp -> {
                setUiState { copy(otp = event.otp) }
            }

            OtpValidationScreenEvent.OnExit -> {
                setUiState { copy(otpValidationState = State.Initial) }
            }
        }
    }

    init {
        startResendCodeTimer()
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

    private suspend fun validateOtp(otp: String, phoneNumber: String) {
        validateOtpUseCase(body = ValidateOtpData(otp = otp, phoneNumber = phoneNumber))
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(otpValidationState = State.Loading) }
                }
                resource.onReady { status: Status ->
                    setUiState { copy(otpValidationState = State.Success(status)) }
                }
                resource.onFailure { message: String ->
                    setUiState { copy(otpValidationState = State.Error(message)) }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        resendOtpState = State.Error(
                            it.localizedMessage ?: "An unexpected event occurred!",
                        ),
                    )
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun resendOtp(phoneNumber: String) {
        forgotPassCodeUseCase(body = ForgotPassCodeData(phoneNumber = phoneNumber))
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(resendOtpState = State.Loading) }
                }
                resource.onReady { status: Status ->
                    setUiState { copy(resendOtpState = State.Success(status), ticks = 60) }
                    startResendCodeTimer()
                }
                resource.onFailure { message: String ->
                    setUiState { copy(resendOtpState = State.Error(message)) }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        resendOtpState = State.Error(
                            it.localizedMessage ?: "An unexpected event occurred!",
                        ),
                    )
                }
            }.launchIn(viewModelScope)
    }
}
