package com.bankly.feature.authentication.ui.confirmpin

sealed interface ConfirmPinScreenEvent {
    data class OnDoneClick(
        val defaultPin: String,
        val newPin: String,
        val confirmPin: List<String>
    ) : ConfirmPinScreenEvent

    data class OnSetPins(
        val defaultPin: String,
        val newPin: String,
    ) : ConfirmPinScreenEvent

    data class OnEnterConfirmPin(val confirmPin: List<String>, val newPin: String) :
        ConfirmPinScreenEvent

    object OnDismissErrorDialog : ConfirmPinScreenEvent
}
