package com.bankly.feature.authentication.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyButton
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R
import com.bankly.feature.authentication.viewmodel.LoginUiEvent
import com.bankly.feature.authentication.viewmodel.LoginUiState
import com.bankly.feature.authentication.viewmodel.LoginViewModel

@Composable
internal fun LoginRoute(
    onLoginClick: (phoneNumber: String, passCode: String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = uiState,
        onLoginClick = { phoneNumber, passCode ->
            viewModel.sendEvent(LoginUiEvent.Login(phoneNumber, passCode))
        }
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onLoginClick: (phoneNumber: String, passCode: String) -> Unit,
) {
    var phoneNumber by remember() { mutableStateOf(TextFieldValue()) }
    var passcode by remember() { mutableStateOf(TextFieldValue()) }
    val isEnabled by remember(
        phoneNumber.text, passcode.text, uiState
    ) {
        mutableStateOf(
            phoneNumber.text.isNotEmpty() &&
                    passcode.text.isNotEmpty() &&
                    uiState !is LoginUiState.Loading
        )
    }
    val isPhoneNumberError by remember { mutableStateOf(false) }
    val isPassCodeError by remember { mutableStateOf(false) }
    val phoneNumberFeedBack by remember { mutableStateOf("") }
    val passCodeFeedback by remember { mutableStateOf("") }

    Log.d("Debug", "Ui state debug: $uiState")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BanklyTitleBar(
                    onBackClick = {},
                    title = stringResource(R.string.msg_log_in),
                    subTitle = buildAnnotatedString {
                        append(stringResource(R.string.msg_login_screen_subtitle))
                    }
                )

                BanklyInputField(
                    textFieldValue = phoneNumber,
                    onTextFieldValueChange = { textFieldValue ->
                        phoneNumber = textFieldValue
                    },
                    placeholderText = stringResource(R.string.msg_phone_number_sample),
                    labelText = "Phone Number",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone
                    ),
                    isError = isPhoneNumberError,
                    feedbackText = phoneNumberFeedBack
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
                        keyboardType = KeyboardType.Password
                    ),
                    isError = isPassCodeError,
                    feedbackText = passCodeFeedback
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
                    onClick = {}
                )
            }
        }

        item {
            BanklyButton(
                text = stringResource(R.string.title_log_in),
                onClick = { onLoginClick(phoneNumber.text, passcode.text) },
                isEnabled = isEnabled
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    BanklyTheme {
        LoginScreen(
            onLoginClick = { _, _ -> },
            uiState = LoginUiState.Initial
        )
    }
}