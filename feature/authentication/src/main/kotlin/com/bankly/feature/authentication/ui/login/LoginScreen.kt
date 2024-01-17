package com.bankly.feature.authentication.ui.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyAccessPinInputField
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R
import com.bankly.feature.authentication.ui.validateotp.OtpValidationScreenEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onSetUpAccessPin: (String) -> Unit,
    onTerminalUnAssigned: () -> Unit,
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        if (screenState.isResetAccessPin) {
            viewModel.sendEvent(LoginScreenEvent.ShowExitResetPinWarningDialog)
        } else {
            onBackPress()
        }
    }

    LoginScreen(
        onBackPress = onBackPress,
        screenState = screenState,
        onUiEvent = { uiEvent: LoginScreenEvent -> viewModel.sendEvent(uiEvent) },
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach { oneShotState: LoginScreenOneShotState ->
            when (oneShotState) {
                LoginScreenOneShotState.OnLoginSuccess -> onLoginSuccess()
                is LoginScreenOneShotState.OnSetUpAccessPin -> {
                    viewModel.sendEvent(LoginScreenEvent.ClearAccessPinInputField)
                    viewModel.sendEvent(LoginScreenEvent.RestoreLoginMode)
                    onSetUpAccessPin(oneShotState.defaultPin)
                }

                LoginScreenOneShotState.OnTerminalUnAssigned -> onTerminalUnAssigned()
            }
        }.launchIn(this)
    })
}

@Composable
internal fun LoginScreen(
    onBackPress: () -> Unit,
    screenState: LoginScreenState,
    onUiEvent: (LoginScreenEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                onBackPress = {
                    if (screenState.isResetAccessPin) {
                        onUiEvent(LoginScreenEvent.ShowExitResetPinWarningDialog)
                    } else {
                        onBackPress()
                    }
                },
                title = stringResource(if (screenState.isResetAccessPin) R.string.msg_reset_access_pin else R.string.msg_log_in),
                subTitle = buildAnnotatedString {
                    append(
                        stringResource(
                            if (screenState.isResetAccessPin) {
                                R.string.msg_eneter_default_access_code_subtitle
                            } else {
                                R.string.msg_login_screen_subtitle
                            },
                        ),
                    )
                },
                isLoading = screenState.isLoading,
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                BanklyAccessPinInputField(
                    passCode = screenState.passCode,
                    isError = screenState.isPassCodeError,
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (screenState.isResetAccessPin.not()) {
                    BanklyClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.bodyMedium.toSpanStyle(),
                            ) {
                                append(stringResource(R.string.msg_forgot_access_pin))
                            }
                            append("\n")
                            withStyle(
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                ).toSpanStyle(),
                            ) { append(stringResource(R.string.action_reset_access_pin)) }
                        },
                        onClick = {
                            onUiEvent(LoginScreenEvent.ClearAccessPinInputField)
                            onUiEvent(LoginScreenEvent.OnResetAccessPinClick)
                        },
                        isEnabled = screenState.isUserInputEnabled,
                        backgroundShape = MaterialTheme.shapes.medium,
                    )
                } else {
                    BanklyClickableText(
                        text = if (screenState.ticks == 0) {
                            buildAnnotatedString {
                                withStyle(
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                    ).toSpanStyle(),
                                ) { append(stringResource(R.string.action_resend_code)) }
                            }
                        } else {
                            buildAnnotatedString {
                                withStyle(
                                    style = MaterialTheme.typography.bodyMedium.toSpanStyle(),
                                ) {
                                    append(stringResource(R.string.msg_resend_code_in))
                                }
                                append(" ")
                                withStyle(
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                    ).toSpanStyle(),
                                ) { append("${screenState.ticks}s") }
                            }
                        },
                        onClick = {
                            onUiEvent(LoginScreenEvent.OnResendAccessCodeClick)
                        },
                        isEnabled = screenState.isResendCodeTextButtonEnabled,
                    )
                }
            }

            item {
                BanklyNumericKeyboard(
                    onKeyPressed = { key ->
                        when (key) {
                            PassCodeKey.DELETE -> {
                                val index =
                                    screenState.passCode.indexOfLast { it.isNotEmpty() }
                                if (index != -1) {
                                    val newPassCode = screenState.passCode.toMutableList()
                                    newPassCode[index] = ""
                                    onUiEvent(LoginScreenEvent.OnEnterPassCode(passCode = newPassCode))
                                }
                            }

                            PassCodeKey.DONE -> {
                                onUiEvent(
                                    LoginScreenEvent.OnLoginClick(
                                        screenState.passCode.joinToString(""),
                                    ),
                                )
                            }

                            else -> {
                                val index =
                                    screenState.passCode.indexOfFirst { it.isEmpty() }
                                if (index != -1) {
                                    val newPassCode = screenState.passCode.toMutableList()
                                    newPassCode[index] = key.value
                                    onUiEvent(LoginScreenEvent.OnEnterPassCode(passCode = newPassCode))
                                }
                            }
                        }
                    },
                    isKeyPadEnabled = screenState.isUserInputEnabled,
                    isDoneKeyEnabled = screenState.isLoginButtonEnabled,
                )
            }
        }
    }

    BanklyCenterDialog(
        title = stringResource(R.string.title_login_error),
        subtitle = screenState.errorDialogMessage,
        showDialog = screenState.showErrorDialog,
        icon = BanklyIcons.ErrorAlert,
        positiveActionText = stringResource(R.string.action_dismiss),
        positiveAction = {
            onUiEvent(LoginScreenEvent.OnDismissErrorDialog)
        },
        onDismissDialog = {
            onUiEvent(LoginScreenEvent.OnDismissErrorDialog)
        },
    )

    BanklyCenterDialog(
        title = stringResource(R.string.exit_warning),
        subtitle = stringResource(R.string.msg_are_you_sure_you_want_to_discontinue_re_setting_your_access_pin),
        showDialog = screenState.showExitResetPinDialog,
        icon = BanklyIcons.ErrorAlert,
        positiveActionText = stringResource(R.string.action_no),
        negativeActionText = stringResource(R.string.action_yes),
        positiveAction = {
            onUiEvent(LoginScreenEvent.OnDismissExitResetPinWarningDialog)
        },
        negativeAction = {
            onUiEvent(LoginScreenEvent.RestoreLoginMode)
            onUiEvent(LoginScreenEvent.ClearAccessPinInputField)
        },
        onDismissDialog = {
            onUiEvent(LoginScreenEvent.OnDismissExitResetPinWarningDialog)
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    BanklyTheme {
        LoginScreen(
            onBackPress = {},
            screenState = LoginScreenState(),
            onUiEvent = {},
        )
    }
}
