package com.bankly.feature.authentication.ui.validatepasscode

import androidx.compose.ui.text.input.TextFieldValue

sealed interface ValidatePassCodeScreenEvent {
    data class OnValidatePassCode(val password: String) : ValidatePassCodeScreenEvent
    data class OnEnterPassCode(val passCodeTFV: TextFieldValue) : ValidatePassCodeScreenEvent
    object OnDismissErrorDialog : ValidatePassCodeScreenEvent
}
