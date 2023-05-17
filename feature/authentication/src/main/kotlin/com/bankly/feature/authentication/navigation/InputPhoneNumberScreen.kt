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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.designsystem.component.BanklyButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Composable
fun InputPhoneNumberScreen() {
    var phoneNumber by remember() { mutableStateOf(TextFieldValue()) }
    val isEnabled by remember(phoneNumber.text) { mutableStateOf(phoneNumber.text.isNotEmpty()) }
    val isError by remember { mutableStateOf(false) }

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
                    title = stringResource(R.string.title_recover_passcode),
                    subTitle = buildAnnotatedString {
                        append(stringResource(R.string.msg_enter_phone_number_to_reset))
                    },
                )

                BanklyInputField(
                    textFieldValue = phoneNumber,
                    onTextFieldValueChange = { textFieldValue ->
                        phoneNumber = textFieldValue
                    },
                    placeholderText = stringResource(R.string.msg_phone_number_sample),
                    labelText = "Phone Number",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = isError
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
fun InputPhoneNumberScreenPreview() {
    BanklyTheme {
        InputPhoneNumberScreen()
    }
}