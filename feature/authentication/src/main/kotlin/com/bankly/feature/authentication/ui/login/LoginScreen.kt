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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.sealed.State
import com.bankly.feature.authentication.R

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onRecoverPassCodeClick: () -> Unit,
) {
    BackHandler { onBackPress() }
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreen(
        onLoginSuccess = onLoginSuccess,
        onBackPress = onBackPress,
        onRecoverPassCodeClick = onRecoverPassCodeClick,
        screenState = screenState,
        onUiEvent = { uiEvent: LoginScreenEvent -> viewModel.sendEvent(uiEvent) },
    )
}

@Composable
internal fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onRecoverPassCodeClick: () -> Unit,
    screenState: LoginScreenState,
    onUiEvent: (LoginScreenEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.msg_log_in),
                subTitle = buildAnnotatedString {
                    append(stringResource(R.string.msg_login_screen_subtitle))
                },
                isLoading = screenState.loginState is State.Loading,
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BanklyInputField(
                        textFieldValue = screenState.phoneNumberTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(LoginScreenEvent.OnEnterPhoneNumber(textFieldValue))
                        },
                        isEnabled = screenState.isUserInputEnabled,
                        placeholderText = stringResource(R.string.msg_phone_number_sample),
                        labelText = stringResource(R.string.label_phone_number),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Phone,
                        ),
                        isError = screenState.isPhoneNumberError,
                        feedbackText = screenState.phoneNumberFeedBack,
                    )

                    BanklyInputField(
                        textFieldValue = screenState.passCodeTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(LoginScreenEvent.OnEnterPassCode(textFieldValue))
                        },
                        isEnabled = screenState.isUserInputEnabled,
                        placeholderText = stringResource(R.string.msg_enter_passcode),
                        labelText = stringResource(R.string.msg_passcode_label),
                        isPasswordField = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                        ),
                        isError = screenState.isPassCodeError,
                        feedbackText = screenState.passCodeFeedBack,
                    )

                    BanklyClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.bodyMedium.toSpanStyle(),
                            ) {
                                append(stringResource(R.string.msg_forgot_passcode))
                            }
                            append(" ")
                            withStyle(
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                ).toSpanStyle(),
                            ) { append(stringResource(R.string.action_recover_passcode)) }
                        },
                        onClick = onRecoverPassCodeClick,
                        isEnabled = screenState.isUserInputEnabled,
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
                        onUiEvent(
                            LoginScreenEvent.OnLoginClick(
                                screenState.phoneNumberTFV.text,
                                screenState.passCodeTFV.text,
                            ),
                        )
                    },
                    isEnabled = screenState.isLoginButtonEnabled,
                )
            }
        }
    }

    when (val state = screenState.loginState) {
        State.Initial, State.Loading -> {}
        is State.Error -> {
            BanklyActionDialog(
                title = stringResource(R.string.title_login_error),
                subtitle = state.message,
                positiveActionText = stringResource(R.string.action_okay),
            )
        }

        is State.Success -> {
            onUiEvent(LoginScreenEvent.OnExit)
            onLoginSuccess()
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    BanklyTheme {
        LoginScreen(
            onLoginSuccess = {},
            onBackPress = {},
            onRecoverPassCodeClick = {},
            screenState = LoginScreenState(),
            onUiEvent = {},
        )
    }
}
