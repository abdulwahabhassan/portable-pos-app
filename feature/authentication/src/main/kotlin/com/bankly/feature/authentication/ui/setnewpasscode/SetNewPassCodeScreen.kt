package com.bankly.feature.authentication.ui.setnewpasscode

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.model.State
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Composable
internal fun SetNewPassCodeRoute(
    viewModel: SetNewPassCodeViewModel = hiltViewModel(),
    phoneNumber: String,
    otp: String,
    onSetNewPassCodeSuccess: (String) -> Unit,
    onBackPress: () -> Unit
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    SetNewPassCodeScreen(
        screenState = screenState,
        phoneNumber = phoneNumber,
        otp = otp,
        onSetNewPassCodeSuccess = onSetNewPassCodeSuccess,
        onBackPress = onBackPress,
        onUiEvent = { uiEvent: SetNewPassCodeScreenEvent -> viewModel.sendEvent(uiEvent) }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SetNewPassCodeScreen(
    screenState: SetNewPassCodeScreenState,
    phoneNumber: String,
    otp: String,
    onSetNewPassCodeSuccess: (String) -> Unit,
    onBackPress: () -> Unit,
    onUiEvent: (SetNewPassCodeScreenEvent) -> Unit
) {
    BackHandler {
        onUiEvent(SetNewPassCodeScreenEvent.OnBackPress)
    }

    if (screenState.shouldShowWarningDialog) {
        BanklyActionDialog(
            title = stringResource(id = R.string.title_confirm_action),
            subtitle = stringResource(id = R.string.msg_are_you_sure_you_do_not_want_to_continue_re_setting_your_passcode),
            positiveActionText = stringResource(R.string.action_yes),
            positiveAction = {
                onBackPress()
            },
            negativeActionText = stringResource(R.string.action_no),
            negativeAction = {
                onUiEvent(SetNewPassCodeScreenEvent.OnDismissWarningDialog)
            }
        )
    }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                title = stringResource(R.string.title_set_new_passcode),
                isLoading = screenState.setNewPassCodeState is State.Loading
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
                        textFieldValue = screenState.passCodeTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(SetNewPassCodeScreenEvent.OnEnterPasscode(textFieldValue))
                        },
                        placeholderText = stringResource(R.string.msg_enter_passcode),
                        labelText = stringResource(R.string.msg_passcode_label),
                        isPasswordField = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        isError = screenState.isPassCodeError,
                        feedbackText = screenState.passCodeFeedBack,
                        isEnabled = screenState.isUserInputEnabled
                    )

                    BanklyInputField(
                        textFieldValue = screenState.confirmPassCodeTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(
                                SetNewPassCodeScreenEvent.OnEnterConfirmPasscode(
                                    textFieldValue
                                )
                            )
                        },
                        placeholderText = stringResource(R.string.msg_enter_confirm_passcode),
                        labelText = stringResource(R.string.msg_confirm_passcode_label),
                        isPasswordField = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        isError = screenState.isConfirmPassCodeError,
                        feedbackText = screenState.confirmPassCodeFeedBack,
                        isEnabled = screenState.isUserInputEnabled
                    )
                }
            }

            item {
                BanklyFilledButton(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.action_done),
                    onClick = {
                        onUiEvent(
                            SetNewPassCodeScreenEvent.OnDoneClick(
                                passCode = screenState.passCodeTFV.text,
                                confirmPassCode = screenState.confirmPassCodeTFV.text,
                                phoneNumber = phoneNumber,
                                otp = otp
                            )
                        )
                    },
                    isEnabled = screenState.isDoneButtonEnabled
                )
            }

        }
    }

    when (val state = screenState.setNewPassCodeState) {
        is State.Initial, is State.Loading -> {}
        is State.Error -> {
            BanklyActionDialog(
                title = stringResource(R.string.title_reset_passcode_error),
                subtitle = state.message,
                positiveActionText = stringResource(R.string.action_okay)
            )
        }

        is State.Success -> {
            onUiEvent(SetNewPassCodeScreenEvent.OnExit)
            onSetNewPassCodeSuccess(state.data.message)
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun SetNewPassCodeScreenPreview() {
    BanklyTheme {
        SetNewPassCodeScreen(
            screenState = SetNewPassCodeScreenState(),
            onSetNewPassCodeSuccess = {},
            phoneNumber = "",
            otp = "",
            onBackPress = {},
            onUiEvent = {}
        )
    }
}