package com.bankly.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import kotlinx.datetime.LocalDate

@Composable
fun BanklyDateInputField(
    date: LocalDate?,
    title: String,
    onQueryChange: (TextFieldValue) -> Unit,
    placeHolder: String,
    onClick: () -> Unit,
    isError: Boolean,
    feedBackText: String,
    isEnabled: Boolean,
    horizontalPadding: Dp = 24.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    BanklyInputField(
        textFieldValue = if (date != null) TextFieldValue(date.toString()) else TextFieldValue(),
        onTextFieldValueChange = onQueryChange,
        trailingIcon = BanklyIcons.ChevronDown,
        isEnabled = isEnabled,
        readOnly = true,
        labelText = title,
        feedbackText = feedBackText,
        isError = isError,
        placeholderText = placeHolder,
        onTrailingIconClick = onClick,
        horizontalPadding = horizontalPadding,
        textStyle = textStyle,
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
fun BanklyDateInputFieldPreview() {
    BanklyTheme {
        BanklyDateInputField(
            date = LocalDate(2023, 10, 1),
            title = "Start date",
            onQueryChange = {},
            placeHolder = "YYY-MM-DD",
            onClick = {},
            isError = false,
            feedBackText = "",
            isEnabled = true,
        )
    }
}
