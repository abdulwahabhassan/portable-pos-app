package com.bankly.feature.authentication.ui.passcode

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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.ActionDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Immutable
data class RecoverPassCodeScreenUiState(
    val phoneNumber: TextFieldValue = TextFieldValue(),
    val isPhoneNumberError: Boolean = false,
    val phoneNumberFeedBack: String = "",
) {
    val isSendCodeButtonEnabled: Boolean
        get() = phoneNumber.text.isNotEmpty() && phoneNumber.text.length == 11 && !isPhoneNumberError
}

@Composable
fun rememberRecoverPassCodeScreenUiState(): MutableState<RecoverPassCodeScreenUiState> =
    remember { mutableStateOf(RecoverPassCodeScreenUiState()) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RecoverPassCodeScreen(
    viewModel: RecoverPassCodeViewModel = hiltViewModel(),
    onRecoverPassCodeSuccess: (String) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val recoverPassCodeState by viewModel.uiState.collectAsStateWithLifecycle()
    var recoverPassCodeScreenUiState by rememberRecoverPassCodeScreenUiState()

    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackClick = onBackButtonClick,
                title = stringResource(R.string.title_recover_passcode),
                subTitle = buildAnnotatedString {
                    append(stringResource(R.string.msg_enter_phone_number_to_reset))
                },
                isLoading = recoverPassCodeState is RecoverPassCodeState.Loading
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BanklyInputField(
                        textFieldValue = recoverPassCodeScreenUiState.phoneNumber,
                        onTextFieldValueChange = { textFieldValue ->
                            recoverPassCodeScreenUiState =
                                recoverPassCodeScreenUiState.copy(phoneNumber = textFieldValue)

                        },
                        isEnabled = recoverPassCodeState !is RecoverPassCodeState.Loading,
                        placeholderText = stringResource(R.string.msg_phone_number_sample),
                        labelText = "Phone Number",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = recoverPassCodeScreenUiState.isPhoneNumberError
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
                        viewModel.sendEvent(
                            RecoverPassCodeUiEvent.RecoverPassCode(
                                recoverPassCodeScreenUiState.phoneNumber.text
                            )
                        )
                    },
                    isEnabled = recoverPassCodeScreenUiState.isSendCodeButtonEnabled && recoverPassCodeState !is RecoverPassCodeState.Loading
                )
            }
        }
    }

    when (val state = recoverPassCodeState) {
        is RecoverPassCodeState.Initial -> {}
        is RecoverPassCodeState.Loading -> {}
        is RecoverPassCodeState.Error -> {
            ActionDialog(
                title = stringResource(R.string.title_recover_passcode_error),
                subtitle = state.errorMessage,
                positiveActionText = stringResource(R.string.action_okay),
                positiveAction = {
                    viewModel.sendEvent(RecoverPassCodeUiEvent.ResetState)
                })
        }

        is RecoverPassCodeState.Success -> onRecoverPassCodeSuccess(recoverPassCodeScreenUiState.phoneNumber.text)
    }

}

@Composable
@Preview(showBackground = true)
private fun RecoverPassCodeScreenPreview() {
    BanklyTheme {
        RecoverPassCodeScreen(
            onRecoverPassCodeSuccess = {},
            onBackButtonClick = {}
        )
    }
}