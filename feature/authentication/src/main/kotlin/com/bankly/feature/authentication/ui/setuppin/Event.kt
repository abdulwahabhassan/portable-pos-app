package com.bankly.feature.authentication.ui.setuppin

sealed interface SetPinScreenEvent {
    data class OnEnterNewPinDoneClick(val defaultPin: String, val newPin: String) :
        SetPinScreenEvent

    data class OnEnterNewPin(val pin: List<String>) : SetPinScreenEvent
    data class SetDefaultPin(val defaultPin: String) : SetPinScreenEvent
    object OnDismissOnBackPressWarningDialog : SetPinScreenEvent
    object OnBackPress : SetPinScreenEvent
}
