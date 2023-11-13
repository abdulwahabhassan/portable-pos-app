package com.bankly.feature.authentication.ui.login

import com.bankly.core.common.viewmodel.OneShotState

data class LoginScreenState(
    val passCode: List<String> = List(6) { "" },
    val isPassCodeError: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val isLoading: Boolean = false,
) {
    val isLoginButtonEnabled: Boolean
        get() = passCode.isNotEmpty() && passCode.all { digit: String -> digit.isNotEmpty() } &&
                isPassCodeError.not() && isLoading.not()
    val isUserInputEnabled: Boolean
        get() = isLoading.not()
}

sealed interface LoginScreenOneShotState : OneShotState {
    object OnLoginSuccess : LoginScreenOneShotState
    object OnSetUpAccessPin : LoginScreenOneShotState
    object OnTerminalUnAssigned : LoginScreenOneShotState
}
