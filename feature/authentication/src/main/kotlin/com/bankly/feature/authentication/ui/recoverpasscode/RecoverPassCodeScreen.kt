package com.bankly.feature.authentication.ui.recoverpasscode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.bankly.core.sealed.State
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Composable
internal fun RecoverPassCodeRoute(
    viewModel: RecoverPassCodeViewModel = hiltViewModel(),
    onRecoverPassCodeSuccess: (String) -> Unit,
    onBackPress: () -> Unit
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    RecoverPassCodeScreen(
        screenState = screenState,
        onRecoverPassCodeSuccess = onRecoverPassCodeSuccess,
        onBackPress = onBackPress,
        onUiEvent = { uiEvent: RecoverPassCodeScreenEvent -> viewModel.sendEvent(uiEvent) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RecoverPassCodeScreen(
    screenState: RecoverPassCodeScreenState,
    onRecoverPassCodeSuccess: (String) -> Unit,
    onBackPress: () -> Unit,
    onUiEvent: (RecoverPassCodeScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_recover_passcode),
                subTitle = buildAnnotatedString {
                    append(stringResource(R.string.msg_enter_phone_number_to_reset))
                },
                isLoading = screenState.recoverPassCodeState is State.Loading
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BanklyInputField(
                        textFieldValue = screenState.phoneNumberTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(RecoverPassCodeScreenEvent.OnEnterPhoneNumber(textFieldValue))
                        },
                        isEnabled = screenState.isUserInputEnabled,
                        placeholderText = stringResource(R.string.msg_phone_number_sample),
                        labelText = "Phone Number",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = screenState.isPhoneNumberError,
                        feedbackText = screenState.phoneNumberFeedBack
                    )
                }
            }

            item {
                BanklyFilledButton(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.action_send_code),
                    onClick = {
                        onUiEvent(RecoverPassCodeScreenEvent.OnSendCodeClick(screenState.phoneNumberTFV.text))
                    },
                    isEnabled = screenState.isSendCodeButtonEnabled
                )
            }
        }
    }

    when (val state = screenState.recoverPassCodeState) {
        is State.Initial, State.Loading -> {}
        is State.Error -> {
            BanklyActionDialog(
                title = stringResource(R.string.title_recover_passcode_error),
                subtitle = state.message,
                positiveActionText = stringResource(R.string.action_okay)
            )
        }

        is State.Success -> {
            onUiEvent(RecoverPassCodeScreenEvent.OnExit)
            onRecoverPassCodeSuccess(screenState.phoneNumberTFV.text)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RecoverPassCodeScreenPreview() {
    BanklyTheme {
        RecoverPassCodeScreen(
            screenState = RecoverPassCodeScreenState(),
            onRecoverPassCodeSuccess = {},
            onBackPress = {},
            onUiEvent = {}
        )
    }
}