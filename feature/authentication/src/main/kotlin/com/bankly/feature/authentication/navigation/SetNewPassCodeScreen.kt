package com.bankly.feature.authentication.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
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
import com.bankly.core.designsystem.component.BanklyButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R


@Composable
fun SetNewPassCodeScreen() {
    var passcode by remember() { mutableStateOf(TextFieldValue()) }
    var confirmPassCode by remember() { mutableStateOf(TextFieldValue()) }
    var isPassCodeError by remember() { mutableStateOf(false) }
    var isConfirmPassCodeError by remember() { mutableStateOf(false) }
    var passCodeMessage by remember() { mutableStateOf("") }
    var confirmPassCodeMessage by remember() { mutableStateOf("") }
    var isPassCodeEnabled by remember() { mutableStateOf(true) }
    var isConfirmPassCodeEnabled by remember() { mutableStateOf(true) }

    val isEnabled by remember(
        passcode.text,
        confirmPassCode.text
    ) {
        mutableStateOf(
            passcode.text.isNotEmpty() &&
                    confirmPassCode.text.isNotEmpty() &&
                    !isPassCodeError &&
                    !isConfirmPassCodeError
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BanklyTitleBar(
                    title = stringResource(R.string.title_set_new_passcode)
                )

                BanklyInputField(
                    textFieldValue = passcode,
                    onTextFieldValueChange = { textFieldValue ->
                        passcode = textFieldValue
                    },
                    placeholderText = stringResource(R.string.msg_enter_passcode),
                    labelText = stringResource(R.string.msg_passcode_label),
                    isPasswordField = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    isError = isPassCodeError,
                    feedbackText = passCodeMessage,
                    isEnabled = isPassCodeEnabled
                )

                BanklyInputField(
                    textFieldValue = confirmPassCode,
                    onTextFieldValueChange = { textFieldValue ->
                        confirmPassCode = textFieldValue
                    },
                    placeholderText = stringResource(R.string.msg_enter_confirm_passcode),
                    labelText = stringResource(R.string.msg_confirm_passcode_label),
                    isPasswordField = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    isError = isConfirmPassCodeError,
                    feedbackText = confirmPassCodeMessage,
                    isEnabled = isConfirmPassCodeEnabled
                )

            }

        }

        item {
            BanklyButton(stringResource(R.string.action_send_code), {}, isEnabled)
        }

    }
}


@Composable
@Preview(showBackground = true)
fun SetNewPassCodeScreenPreview() {
    BanklyTheme {
        SetNewPassCodeScreen()
    }
}