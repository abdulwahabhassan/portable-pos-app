package com.bankly.feature.authentication.ui.confirmpin

import com.bankly.core.common.viewmodel.OneShotState

data class ConfirmPinScreenState(
    val defaultPin: String = "",
    val newPin: String = "",
    val confirmPin: List<String> = List(6) { "" },
    val isConfirmPinError: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val isLoading: Boolean = false,
    val pinErrorMessage: String = "",
) {
    val isDoneButtonEnabled: Boolean
        get() = newPin.isNotEmpty() && confirmPin.all { digit: String -> digit.isNotEmpty() } &&
            newPin == confirmPin.joinToString("") && isConfirmPinError.not() &&
            isLoading.not()
    val isUserInputEnabled: Boolean
        get() = isLoading.not()
}

sealed interface ConfirmPinScreenOneShotState : OneShotState {
    data class OnSetPinSuccess(val message: String) : ConfirmPinScreenOneShotState
    object OnSessionExpired : ConfirmPinScreenOneShotState
}
