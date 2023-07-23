package com.bankly.feature.authentication.ui.recoverpasscode

import androidx.compose.ui.text.input.TextFieldValue

sealed interface RecoverPassCodeScreenEvent {
    data class OnSendCodeClick(val phoneNumber: String) : RecoverPassCodeScreenEvent
    data class OnEnterPhoneNumber(val phoneNumberTFV: TextFieldValue) : RecoverPassCodeScreenEvent
    object OnExit : RecoverPassCodeScreenEvent
}