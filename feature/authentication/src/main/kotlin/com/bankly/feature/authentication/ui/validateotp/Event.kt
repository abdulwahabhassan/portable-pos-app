package com.bankly.feature.authentication.ui.validateotp

sealed interface OtpValidationScreenEvent {
    data class OnDoneClick(val otp: String, val phoneNumber: String) : OtpValidationScreenEvent
    data class OnResendOtpClick(val phoneNumber: String) : OtpValidationScreenEvent
    data class OnEnterOtp(val otp: List<String>) : OtpValidationScreenEvent
    object OnExit : OtpValidationScreenEvent
}