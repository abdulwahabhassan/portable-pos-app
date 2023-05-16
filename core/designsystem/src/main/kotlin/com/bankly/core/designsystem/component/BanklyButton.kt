package com.bankly.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bankly.core.designsystem.theme.BanklyTheme


@Composable
fun BanklyButton(
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .height(50.dp),
        enabled = isEnabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer
        )

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BanklyButtonPreview() {
    BanklyTheme {
        BanklyButton(
            text = "Pay",
            onClick = {},
            isEnabled = true
        )
    }
}
