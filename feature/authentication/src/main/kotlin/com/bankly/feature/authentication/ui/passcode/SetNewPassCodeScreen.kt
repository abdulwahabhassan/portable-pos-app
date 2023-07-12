package com.bankly.feature.authentication.ui.passcode

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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R


@Immutable
data class SetNewPassCodeScreenUiState(
    val passCode: TextFieldValue = TextFieldValue(text = ""),
    val confirmPassCode: TextFieldValue = TextFieldValue(text = ""),
    val isPassCodeError: Boolean = false,
    val isConfirmPassCodeError: Boolean = false,
    val passCodeFeedBack: String = "",
    val confirmPassCodeFeedBack: String = "",
    val phoneNumber: String = "",
    val otp: String = "",
    val showActionDialog: Boolean = false
) {
    val isDoneButtonEnabled: Boolean
        get() = passCode.text.isNotEmpty() && confirmPassCode.text.isNotEmpty() && !isPassCodeError && !isConfirmPassCodeError
}

@Composable
fun rememberSetNewPassCodeScreenUiState(
    phoneNumber: String,
    otp: String,
): MutableState<SetNewPassCodeScreenUiState> =
    remember(phoneNumber, otp) {
        mutableStateOf(SetNewPassCodeScreenUiState(phoneNumber = phoneNumber, otp = otp))
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SetNewPassCodeScreen(
    viewModel: SetNewPassCodeViewModel = hiltViewModel(),
    phoneNumber: String,
    otp: String,
    onSetNewPassCodeSuccess: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val setNewPassCodeState by viewModel.state.collectAsStateWithLifecycle()
    var setNewPassCodeUiState by rememberSetNewPassCodeScreenUiState(phoneNumber, otp)
    BackHandler {
        setNewPassCodeUiState = setNewPassCodeUiState.copy(showActionDialog = true)
    }

    if (setNewPassCodeUiState.showActionDialog) {
        BanklyActionDialog(
            title = stringResource(id = R.string.title_confirm_action),
            subtitle = stringResource(id = R.string.msg_are_you_sure_you_do_not_want_to_continue_re_setting_your_passcode),
            positiveActionText = stringResource(R.string.action_yes),
            positiveAction = {
                onBackClick()
            },
            negativeActionText = stringResource(R.string.action_no),
            negativeAction = {
                setNewPassCodeUiState = setNewPassCodeUiState.copy(showActionDialog = false)
            })
    }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                title = stringResource(R.string.title_set_new_passcode),
                isLoading = setNewPassCodeState is SetNewPassCodeState.Loading
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
                        textFieldValue = setNewPassCodeUiState.passCode,
                        onTextFieldValueChange = { textFieldValue ->
                            setNewPassCodeUiState =
                                setNewPassCodeUiState.copy(passCode = textFieldValue)
                        },
                        placeholderText = stringResource(R.string.msg_enter_passcode),
                        labelText = stringResource(R.string.msg_passcode_label),
                        isPasswordField = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        isError = setNewPassCodeUiState.isPassCodeError,
                        feedbackText = setNewPassCodeUiState.passCodeFeedBack,
                        isEnabled = setNewPassCodeState !is SetNewPassCodeState.Loading
                    )

                    BanklyInputField(
                        textFieldValue = setNewPassCodeUiState.confirmPassCode,
                        onTextFieldValueChange = { textFieldValue ->
                            setNewPassCodeUiState =
                                setNewPassCodeUiState.copy(confirmPassCode = textFieldValue)
                        },
                        placeholderText = stringResource(R.string.msg_enter_confirm_passcode),
                        labelText = stringResource(R.string.msg_confirm_passcode_label),
                        isPasswordField = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        isError = setNewPassCodeUiState.isConfirmPassCodeError,
                        feedbackText = setNewPassCodeUiState.confirmPassCodeFeedBack,
                        isEnabled = setNewPassCodeState !is SetNewPassCodeState.Loading
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
                        viewModel.sendEvent(
                            SetNewPassCodeUiEvent.SetNewPassCode(
                                passCode = setNewPassCodeUiState.passCode.text,
                                confirmPassCode = setNewPassCodeUiState.confirmPassCode.text,
                                phoneNumber = setNewPassCodeUiState.phoneNumber,
                                otp = setNewPassCodeUiState.otp
                            )
                        )
                    },
                    isEnabled = setNewPassCodeUiState.isDoneButtonEnabled && setNewPassCodeState !is SetNewPassCodeState.Loading
                )
            }

        }
    }

    when (val state = setNewPassCodeState) {
        is SetNewPassCodeState.Initial -> {}
        is SetNewPassCodeState.Loading -> {}
        is SetNewPassCodeState.Error -> {
            BanklyActionDialog(
                title = stringResource(R.string.title_reset_passcode_error),
                subtitle = state.errorMessage,
                positiveActionText = stringResource(R.string.action_okay),
                positiveAction = {
                    viewModel.sendEvent(SetNewPassCodeUiEvent.ResetState)
                })
        }

        is SetNewPassCodeState.Success -> {
            onSetNewPassCodeSuccess(state.message)
        }
    }

}


@Composable
@Preview(showBackground = true)
private fun SetNewPassCodeScreenPreview() {
    BanklyTheme {
        SetNewPassCodeScreen(
            onSetNewPassCodeSuccess = {},
            phoneNumber = "",
            otp = "",
            onBackClick = {}
        )
    }
}