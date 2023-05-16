package com.bankly.feature.authentication.navigation

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
import com.bankly.core.designsystem.component.BanklyButton
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Composable
fun LoginScreen() {
    var phoneNumber by remember() { mutableStateOf(TextFieldValue()) }
    var passcode by remember() { mutableStateOf(TextFieldValue()) }
    val isEnabled by remember(
        phoneNumber.text,
        passcode.text
    ) { mutableStateOf(phoneNumber.text.isNotEmpty() && passcode.text.isNotEmpty()) }

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
                    subTitle = stringResource(R.string.msg_login_screen_subtitle),
                    currentPage = 0,
                    totalPage = 0
                )

                BanklyInputField(
                    textFieldValue = phoneNumber,
                    onTextFieldValueChange = { textFieldValue ->
                        phoneNumber = textFieldValue
                    },
                    hint = stringResource(R.string.msg_phone_number_sample),
                    label = "Phone Number",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                BanklyInputField(
                    textFieldValue = passcode,
                    onTextFieldValueChange = { textFieldValue ->
                        passcode = textFieldValue
                    },
                    hint = stringResource(R.string.msg_enter_passcode),
                    label = stringResource(R.string.msg_passcode_label),
                    isPasswordField = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword
                    )
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
            BanklyButton(stringResource(R.string.action_log_in), {}, isEnabled)
        }
    }


}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    BanklyTheme {
        LoginScreen()
    }
}