package com.bankly.feature.authentication.ui.login

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.sealed.State

data class LoginScreenState(
    val passCode: List<String> = List(6) { "" },
    val isPassCodeError: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val isLoading: Boolean = false,
    val isResetAccessPin: Boolean = false,
    val showExitResetPinDialog: Boolean = false,
    val ticks: Int = 60,
) {
    val isLoginButtonEnabled: Boolean
        get() = passCode.isNotEmpty() && passCode.all { digit: String -> digit.isNotEmpty() } &&
            isPassCodeError.not() && isLoading.not()
    val isUserInputEnabled: Boolean
        get() = isLoading.not()
    val isResendCodeTextButtonEnabled: Boolean
        get() = isUserInputEnabled && ticks == 0
}

sealed interface LoginScreenOneShotState : OneShotState {
    object OnLoginSuccess : LoginScreenOneShotState
    data class OnSetUpAccessPin(val defaultPin: String) : LoginScreenOneShotState
    object OnTerminalUnAssigned : LoginScreenOneShotState
}
