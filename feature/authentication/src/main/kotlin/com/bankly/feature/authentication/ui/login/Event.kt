package com.bankly.feature.authentication.ui.login

sealed interface LoginScreenEvent {
    data class OnLoginClick(val passCode: String) : LoginScreenEvent
    data class OnEnterPassCode(val passCode: List<String>) : LoginScreenEvent
    object OnDismissErrorDialog: LoginScreenEvent
    object ClearAccessPinInputField: LoginScreenEvent
    object RestoreLoginMode: LoginScreenEvent
    object OnResetAccessPinClick: LoginScreenEvent
    object ShowExitResetPinWarningDialog: LoginScreenEvent
    object OnDismissExitResetPinWarningDialog: LoginScreenEvent
}
