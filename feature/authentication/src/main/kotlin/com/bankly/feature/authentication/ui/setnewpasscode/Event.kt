package com.bankly.feature.authentication.ui.setnewpasscode

import androidx.compose.ui.text.input.TextFieldValue

sealed interface SetNewPassCodeScreenEvent {
    object OnBackPress : SetNewPassCodeScreenEvent
    object OnDismissWarningDialog : SetNewPassCodeScreenEvent
    data class OnEnterPasscode(val passCodeTFV: TextFieldValue) : SetNewPassCodeScreenEvent
    data class OnEnterConfirmPasscode(val confirmPassCodeTFV: TextFieldValue) :
        SetNewPassCodeScreenEvent

    data class OnDoneClick(
        val otp: String,
        val phoneNumber: String,
        val passCode: String,
        val confirmPassCode: String
    ) : SetNewPassCodeScreenEvent

    object OnExit : SetNewPassCodeScreenEvent
}