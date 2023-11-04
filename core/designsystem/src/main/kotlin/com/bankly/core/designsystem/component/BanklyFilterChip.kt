package com.bankly.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
fun BanklyFilterChip(
    title: String,
    onClick: (Boolean) -> Unit,
    isSelected: Boolean,
    trailingIcon: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary)
) {
    BanklyClickableText(
        text = buildAnnotatedString {
            withStyle(
                MaterialTheme.typography.bodySmall.copy(color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary)
                    .toSpanStyle()
            ) { append(title) }
        },
        onClick = {
            onClick(isSelected)
        },
        backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
        backgroundShape = MaterialTheme.shapes.extraSmall,
        trailingIcon = trailingIcon,
        textStyle = textStyle
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyFilterChipPreview() {
    BanklyTheme {
        BanklyFilterChip(title = "Credit", onClick = {}, isSelected = true)
    }
}