package com.bankly.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme


@Composable
fun BanklyOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.primary,
    outlineColor: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(50.dp)
            .border(
                width = 1.dp,
                color = if (isEnabled) outlineColor else MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.small
            ),
        enabled = isEnabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer
        )

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyOutlinedButtonPreview1() {
    BanklyTheme {
        BanklyOutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            text = "No",
            onClick = {},
            isEnabled = true
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyOutlinedButtonPreview2() {
    BanklyTheme {
        BanklyOutlinedButton(
            modifier = Modifier.padding(32.dp),
            text = "Yes, cancel",
            onClick = {},
            isEnabled = true,
            backgroundColor = MaterialTheme.colorScheme.errorContainer,
            textColor = MaterialTheme.colorScheme.error,
            outlineColor = Color.Unspecified
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyOutlinedButtonPreview3() {
    BanklyTheme {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            BanklyOutlinedButton(
                modifier = Modifier.weight(1f).padding(16.dp),
                text = "Disabled",
                onClick = {},
                isEnabled = false
            )
            BanklyOutlinedButton(
                modifier = Modifier.weight(1f).padding(16.dp),
                text = "No",
                onClick = {},
                isEnabled = true
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyOutlinedButtonPreview4() {
    BanklyTheme {
        BanklyOutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            text = "Disabled",
            onClick = {},
            isEnabled = false,
            backgroundColor = MaterialTheme.colorScheme.errorContainer,
            textColor = MaterialTheme.colorScheme.error,
            outlineColor = Color.Unspecified
        )
    }
}

