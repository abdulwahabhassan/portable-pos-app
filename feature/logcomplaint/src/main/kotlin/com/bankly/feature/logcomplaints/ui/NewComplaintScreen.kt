package com.bankly.feature.logcomplaints.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.logcomplaint.R
import com.bankly.feature.logcomplaints.ui.component.UploadFile

@Composable
internal fun NewComplaintRoute(
    onBackPress: () -> Unit,
    onSuccessfulLog: (String) -> Unit,
) {
    NewComplaintScreen(
        onBackPress = onBackPress,
        onSuccessfulLog = onSuccessfulLog,
    )
}

@Composable
internal fun NewComplaintScreen(
    onBackPress: () -> Unit,
    onSuccessfulLog: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_new_complaint),
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
        ) {
            item {
                BanklyInputField(
                    textFieldValue = TextFieldValue(),
                    onTextFieldValueChange = { },
                    trailingIcon = BanklyIcons.ChevronDown,
                    readOnly = true,
                    onTrailingIconClick = {
                    },
                    placeholderText = stringResource(R.string.msg_select_complaint_type),
                    labelText = stringResource(R.string.enter_complaint_type),
                    isError = false,
                    feedbackText = "",
                    isEnabled = true,
                )
                Spacer(modifier = Modifier.height(8.dp))
                BanklyInputField(
                    textFieldValue = TextFieldValue(),
                    onTextFieldValueChange = { },
                    readOnly = true,
                    placeholderText = stringResource(R.string.enter_reference_number),
                    labelText = stringResource(R.string.title_reference_number),
                    isError = false,
                    feedbackText = "",
                    isEnabled = true,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = stringResource(R.string.title_upload_file),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    UploadFile(docName = "", onClick = {})
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.msg_document_must_be_injpg_pdf_ms_word_or_excel_formats),
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                BanklyInputField(
                    textFieldValue = TextFieldValue(),
                    onTextFieldValueChange = { },
                    readOnly = true,
                    placeholderText = stringResource(R.string.msg_describe_your_complaint),
                    labelText = stringResource(R.string.title_complaint_message),
                    isError = false,
                    feedbackText = "",
                    isEnabled = true,
                    minLines = 4,
                    maxlines = 4,
                )
                BanklyFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    text = stringResource(R.string.action_send),
                    onClick = {
                        onSuccessfulLog("ID: 38924y4920ID")
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun NewComplaintScreenPreview() {
    BanklyTheme {
        NewComplaintScreen(
            onBackPress = {},
            onSuccessfulLog = {},
        )
    }
}
