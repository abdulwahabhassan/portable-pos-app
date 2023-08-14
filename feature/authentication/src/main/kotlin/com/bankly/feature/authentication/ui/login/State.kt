package com.bankly.feature.authentication.ui.login

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.State
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.Token

data class LoginScreenState(
    val phoneNumberTFV: TextFieldValue = TextFieldValue(text = "08167039661"),
    val passCodeTFV: TextFieldValue = TextFieldValue(text = "Gdz36Val"),
    val isPhoneNumberError: Boolean = false,
    val isPassCodeError: Boolean = false,
    val phoneNumberFeedBack: String = "",
    val passCodeFeedBack: String = "",
    val loginState: State<Token> = State.Initial
) {
    val isLoginButtonEnabled: Boolean
        get() = phoneNumberTFV.text.isNotEmpty() && passCodeTFV.text.isNotEmpty() &&
                isPhoneNumberError.not() && isPassCodeError.not() && loginState !is State.Loading
    val isUserInputEnabled: Boolean
        get() = loginState !is State.Loading
}

sealed interface LoginScreenOneShotState : OneShotState