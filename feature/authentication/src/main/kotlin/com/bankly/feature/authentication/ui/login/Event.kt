package com.bankly.feature.authentication.ui.login

import androidx.compose.ui.text.input.TextFieldValue

sealed interface LoginScreenEvent {
    data class OnLoginClick(val phoneNumber: String, val passCode: String) : LoginScreenEvent
    data class OnEnterPhoneNumber(val phoneNumberTFV: TextFieldValue) : LoginScreenEvent
    data class OnEnterPassCode(val passCodeTFV: TextFieldValue) : LoginScreenEvent
}
