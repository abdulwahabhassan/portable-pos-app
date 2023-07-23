package com.bankly.feature.authentication.ui.createnewpasscode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R


@Composable
fun CreateNewPassCodeScreen() {
    var passcode by remember() { mutableStateOf(TextFieldValue()) }
    val isEnabled by remember(passcode.text) { mutableStateOf(passcode.text.isNotEmpty()) }
    val isPassCodeError by remember { mutableStateOf(false) }
    val passCodeFeedBack by remember { mutableStateOf("") }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            BanklyTitleBar(
                title = stringResource(R.string.msg_new_passcode),
                subTitle = buildAnnotatedString { append(stringResource(R.string.msg_create_new_passcode)) },
                currentPage = 1,
                totalPage = 3
            )
            BanklyInputField(
                textFieldValue = passcode,
                onTextFieldValueChange = { value ->
                    passcode = value
                },
                placeholderText = "Enter new passcode",
                labelText = "Passcode",
                isPasswordField = true,
                isError = isPassCodeError,
                feedbackText = passCodeFeedBack
            )
        }

        item {
            BanklyFilledButton(
                Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.action_continue),
                onClick = { },
                isEnabled = isEnabled
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CreateNewPassCodeScreenPreview() {
    BanklyTheme {
        CreateNewPassCodeScreen()
    }
}