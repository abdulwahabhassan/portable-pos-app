package com.bankly.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme

@Deprecated("Incomplete")
@Composable
fun BanklyDropDownInputField(
    placeholderText: String,
    labelText: String,
    isEnabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    feedbackText: String = "",
    selectionOptions: List<String>
) {
    var bankTFV: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var isDropDownExpanded: Boolean by remember { mutableStateOf(true) }

    Box {
        BanklyInputField(
            textFieldValue = bankTFV,
            onTextFieldValueChange = { textFieldValue ->
                bankTFV = textFieldValue
            },
            readOnly = readOnly,
            isEnabled = isEnabled,
            placeholderText = placeholderText,
            labelText = labelText,
            isError = isError,
            feedbackText = feedbackText,
            trailingIcon = if (isDropDownExpanded) BanklyIcons.Chevron_Up else BanklyIcons.Chevron_Down,
            onTrailingIconClick = {
                isDropDownExpanded = true
            }
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = isDropDownExpanded,
            onDismissRequest = {
                isDropDownExpanded = false
            }
        ) {
            selectionOptions.forEach { option: String ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = { Text(text = option, style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        bankTFV = bankTFV.copy(text = option)
                        isDropDownExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BanklyDropDownInputFieldPreview() {
    BanklyTheme {
        BanklyDropDownInputField(
            placeholderText = "Select bank",
            labelText = "Bank Name",
            selectionOptions = listOf("GTB", "FCMB", "First Bank of Nigeria (FBN)")
        )
    }
}