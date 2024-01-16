package com.bankly.feature.authentication.ui.validateotp

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.entity.Status
import com.bankly.core.model.sealed.State

data class OtpValidationScreenState(
    val otp: List<String> = List(6) { "" },
    val ticks: Int = 60,
    val otpValidationState: State<com.bankly.core.model.entity.Status> = State.Initial,
    val resendOtpState: State<com.bankly.core.model.entity.Status> = State.Initial,
) {
    val isDoneButtonEnabled: Boolean
        get() = otp.all { digit: String -> digit.isNotEmpty() } && otpValidationState !is State.Loading && resendOtpState !is State.Loading
    val isKeyPadEnabled: Boolean
        get() = otpValidationState !is State.Loading && resendOtpState !is State.Loading
    val isResendCodeTextButtonEnabled: Boolean
        get() = otpValidationState !is State.Loading && resendOtpState !is State.Loading && ticks == 0
    val isLoading: Boolean
        get() = otpValidationState is State.Loading || resendOtpState is State.Loading
}

sealed interface OtpValidationScreenOneShotState : OneShotState
