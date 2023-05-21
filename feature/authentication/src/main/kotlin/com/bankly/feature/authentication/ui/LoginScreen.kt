package com.bankly.feature.authentication.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyButton
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R
import com.bankly.feature.authentication.viewmodel.LoginState
import com.bankly.feature.authentication.viewmodel.LoginUiEvent
import com.bankly.feature.authentication.viewmodel.LoginViewModel

@Immutable
data class LoginUiState(
    val phoneNumber: TextFieldValue = TextFieldValue(text = "08167039661"),
    val passCode: TextFieldValue = TextFieldValue(text = "Gdz36Val"),
    val isPhoneNumberError: Boolean = false,
    val isPassCodeError: Boolean = false,
    val phoneNumberFeedBack: String = "",
    val passCodeFeedBack: String = "",
) {
    val isLoginButtonEnabled: Boolean
        get() = phoneNumber.text.isNotEmpty() && passCode.text.isNotEmpty() && !isPhoneNumberError && !isPassCodeError
}

@Composable
fun rememberLoginUiState(): MutableState<LoginUiState> = remember { mutableStateOf(LoginUiState()) }

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onLoginError: (String) -> Unit
) {
    val loginState by viewModel.uiState.collectAsStateWithLifecycle()
    var loginUiState by rememberLoginUiState()

    Log.d("login debug ui state", "$loginUiState")
    Log.d("login debug ui", "$loginUiState")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BanklyTitleBar(
                    onBackClick = {},
                    title = stringResource(R.string.msg_log_in),
                    subTitle = buildAnnotatedString {
                        append(stringResource(R.string.msg_login_screen_subtitle))
                    },
                    isLoading = loginState == LoginState.Loading
                )

                BanklyInputField(
                    textFieldValue = loginUiState.phoneNumber,
                    onTextFieldValueChange = { textFieldValue ->
                        loginUiState = loginUiState.copy(phoneNumber = textFieldValue)
                    },
                    isEnabled = loginState !is LoginState.Loading,
                    placeholderText = stringResource(R.string.msg_phone_number_sample),
                    labelText = "Phone Number",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone
                    ),
                    isError = loginUiState.isPhoneNumberError,
                    feedbackText = loginUiState.phoneNumberFeedBack
                )

                BanklyInputField(
                    textFieldValue = loginUiState.passCode,
                    onTextFieldValueChange = { textFieldValue ->
                        loginUiState = loginUiState.copy(passCode = textFieldValue)
                    },
                    isEnabled = loginState !is LoginState.Loading,
                    placeholderText = stringResource(R.string.msg_enter_passcode),
                    labelText = stringResource(R.string.msg_passcode_label),
                    isPasswordField = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    isError = loginUiState.isPassCodeError,
                    feedbackText = loginUiState.passCodeFeedBack
                )

                BanklyClickableText(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.msg_forgot_passcode))
                        withStyle(
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.primary
                            ).toSpanStyle()
                        ) { append(stringResource(R.string.action_recover_passcode)) }
                    },
                    onClick = {},
                    isEnabled = loginState !is LoginState.Loading,
                )
            }
        }

        item {
            BanklyButton(
                text = stringResource(R.string.title_log_in),
                onClick = {
                    Log.d("login debug", "login button clicked!")
                    viewModel.sendEvent(
                        LoginUiEvent.Login(
                            loginUiState.phoneNumber.text,
                            loginUiState.passCode.text
                        )
                    )
                },
                isEnabled = loginUiState.isLoginButtonEnabled && loginState !is LoginState.Loading
            )
        }
    }

    when (val state = loginState) {
        is LoginState.Initial -> {}
        is LoginState.Loading -> {}
        is LoginState.Error -> {
            onLoginError(state.message)
        }
        is LoginState.Success -> onLoginSuccess()
    }

}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    BanklyTheme {
        LoginScreen(
            onLoginSuccess = {},
            onLoginError = {}
        )
    }
}