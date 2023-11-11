package com.bankly.feature.authentication.ui.validatepasscode

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun ValidatePassCodeRoute(
    viewModel: ValidatePassCodeViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onGoToSettingsScreen: () -> Unit,
) {
    BackHandler { onBackPress() }
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    ValidatePassCodeScreen(
        onBackPress = onBackPress,
        screenState = screenState,
        onUiEvent = { uiEvent: ValidatePassCodeScreenEvent -> viewModel.sendEvent(uiEvent) },
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.oneShotState.onEach { oneShotState: ValidatePassCodeScreenOneShotState ->
            when (oneShotState) {
                ValidatePassCodeScreenOneShotState.GoToSettingsScreen -> onGoToSettingsScreen()
            }
        }.launchIn(this)
    }
}

@Composable
internal fun ValidatePassCodeScreen(
    onBackPress: () -> Unit,
    screenState: ValidatePassCodeScreenState,
    onUiEvent: (ValidatePassCodeScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_enter_password),
                subTitle = buildAnnotatedString {
                    append(stringResource(R.string.msg_enter_your_password_to_enable))
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BanklyInputField(
                        textFieldValue = screenState.passCodeTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(ValidatePassCodeScreenEvent.OnEnterPassCode(textFieldValue))
                        },
                        isEnabled = screenState.isUserInputEnabled,
                        placeholderText = stringResource(R.string.msg_enter_password),
                        labelText = stringResource(R.string.msg_password_label),
                        isPasswordField = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                        ),
                        isError = screenState.isPassCodeError,
                        feedbackText = screenState.passCodeFeedBack,
                    )

                }
            }

            item {
                BanklyFilledButton(
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.action_continue),
                    onClick = {
                        onUiEvent(
                            ValidatePassCodeScreenEvent.OnValidatePassCode(
                                screenState.passCodeTFV.text,
                            ),
                        )
                    },
                    isEnabled = screenState.isContinueButtonEnabled,
                )
            }
        }
    }

    BanklyCenterDialog(
        title = stringResource(R.string.title_error),
        subtitle = screenState.errorDialogMessage,
        positiveActionText = stringResource(R.string.action_dismiss),
        positiveAction = {
            onUiEvent(ValidatePassCodeScreenEvent.OnDismissErrorDialog)
        },
        showDialog = screenState.showErrorDialog,
        onDismissDialog = {
            onUiEvent(ValidatePassCodeScreenEvent.OnDismissErrorDialog)
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    BanklyTheme {
        ValidatePassCodeScreen(
            onBackPress = {},
            screenState = ValidatePassCodeScreenState(),
            onUiEvent = {},
        )
    }
}
