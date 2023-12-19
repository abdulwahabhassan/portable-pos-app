package com.bankly.feature.authentication.ui.setuppin

import com.bankly.core.common.viewmodel.OneShotState

data class SetPinScreenState(
    val defaultPin: String = "",
    val newPin: List<String> = List(6) { "" },
    val isNewPinError: Boolean = false,
    val showOnBackPressScreenWarningDialog: Boolean = false,
) {
    val isDoneButtonEnabled: Boolean
        get() = newPin.isNotEmpty() && newPin.all { digit: String -> digit.isNotEmpty() } &&
            isNewPinError.not()
}

sealed interface SetPinScreenOneShotState : OneShotState {
    data class OnGoToConfirmPinScreen(val defaultPin: String, val newPin: String) : SetPinScreenOneShotState
}
