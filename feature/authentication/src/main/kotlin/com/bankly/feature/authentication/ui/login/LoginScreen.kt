package com.bankly.feature.authentication.ui.login

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.ActionDialog
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Immutable
data class LoginScreenUiState(
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
fun rememberLoginScreenUiState(): MutableState<LoginScreenUiState> = remember { mutableStateOf(LoginScreenUiState()) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onRecoverPassCodeClick: () -> Unit
) {
    val loginState by viewModel.uiState.collectAsStateWithLifecycle()
    var loginScreenUiState by rememberLoginScreenUiState()

    Log.d("login debug ui state", "$loginScreenUiState")
    Log.d("login debug ui", "$loginScreenUiState")

    BackHandler { onBackClick() }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.msg_log_in),
                subTitle = buildAnnotatedString {
                    append(stringResource(R.string.msg_login_screen_subtitle))
                },
                isLoading = loginState == LoginState.Loading
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BanklyInputField(
                        textFieldValue = loginScreenUiState.phoneNumber,
                        onTextFieldValueChange = { textFieldValue ->
                            loginScreenUiState = loginScreenUiState.copy(phoneNumber = textFieldValue)
                        },
                        isEnabled = loginState !is LoginState.Loading,
                        placeholderText = stringResource(R.string.msg_phone_number_sample),
                        labelText = stringResource(R.string.label_phone_number),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Phone
                        ),
                        isError = loginScreenUiState.isPhoneNumberError,
                        feedbackText = loginScreenUiState.phoneNumberFeedBack
                    )

                    BanklyInputField(
                        textFieldValue = loginScreenUiState.passCode,
                        onTextFieldValueChange = { textFieldValue ->
                            loginScreenUiState = loginScreenUiState.copy(passCode = textFieldValue)
                        },
                        isEnabled = loginState !is LoginState.Loading,
                        placeholderText = stringResource(R.string.msg_enter_passcode),
                        labelText = stringResource(R.string.msg_passcode_label),
                        isPasswordField = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        isError = loginScreenUiState.isPassCodeError,
                        feedbackText = loginScreenUiState.passCodeFeedBack
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
                        onClick = onRecoverPassCodeClick,
                        isEnabled = loginState !is LoginState.Loading,
                    )
                }
            }

            item {
                BanklyFilledButton(
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.title_log_in),
                    onClick = {
                        Log.d("login debug", "login button clicked!")
                        viewModel.sendEvent(
                            LoginUiEvent.Login(
                                loginScreenUiState.phoneNumber.text,
                                loginScreenUiState.passCode.text
                            )
                        )
                    },
                    isEnabled = loginScreenUiState.isLoginButtonEnabled && loginState !is LoginState.Loading
                )
            }
        }
    }

    when (val state = loginState) {
        is LoginState.Initial -> {}
        is LoginState.Loading -> {}
        is LoginState.Error -> {
            ActionDialog(
                title = stringResource(R.string.title_login_error),
                subtitle = state.errorMessage,
                positiveActionText = stringResource(R.string.action_okay),
                positiveAction = {
                    viewModel.sendEvent(LoginUiEvent.ResetState)
                })
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
            onBackClick = {},
            onRecoverPassCodeClick = {}
        )
    }
}