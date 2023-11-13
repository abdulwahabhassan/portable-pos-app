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
import com.bankly.core.designsystem.component.BanklyAccessCodeInputField
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyPassCodeInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.sealed.State
import com.bankly.feature.authentication.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onRecoverPassCodeClick: () -> Unit,
    onSetUpAccessPin: () -> Unit,
    onTerminalUnAssigned: () -> Unit
) {
    BackHandler { onBackPress() }

    val screenState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        onBackPress = onBackPress,
        onRecoverPassCodeClick = onRecoverPassCodeClick,
        screenState = screenState,
        onUiEvent = { uiEvent: LoginScreenEvent -> viewModel.sendEvent(uiEvent) },
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach { oneShotState: LoginScreenOneShotState ->
            when (oneShotState) {
                LoginScreenOneShotState.OnLoginSuccess -> onLoginSuccess()
                LoginScreenOneShotState.OnSetUpAccessPin -> onSetUpAccessPin()
                LoginScreenOneShotState.OnTerminalUnAssigned -> onTerminalUnAssigned()
            }
        }.launchIn(this)
    })
}

@Composable
internal fun LoginScreen(
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

                BanklyAccessCodeInputField(
                    passCode = screenState.passCode,
                    isError = screenState.isPassCodeError
                )

                Spacer(modifier = Modifier.height(16.dp))

                BanklyClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.toSpanStyle(),
                        ) {
                            append(stringResource(R.string.msg_forgot_passcode))
                        }
                        append("\n")
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                            ).toSpanStyle(),
                        ) { append(stringResource(R.string.action_recover_passcode)) }
                    },
                    onClick = onRecoverPassCodeClick,
                    isEnabled = screenState.isUserInputEnabled,
                    backgroundShape = MaterialTheme.shapes.medium,
                )

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
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    BanklyTheme {
        LoginScreen(
            onBackPress = {},
            onRecoverPassCodeClick = {},
            screenState = LoginScreenState(),
            onUiEvent = {},
        )
    }
}
