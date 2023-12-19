package com.bankly.feature.eod.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
internal fun ExportEodItem(
    text: String,
    selected: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.inversePrimary
        selected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.tertiary
    }
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Row(
            modifier = Modifier
                .clickable(enabled) { onClick() }
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
            )
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(16.dp))
                trailingIcon()
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = PreviewColor.white)
@Composable
private fun ExportEodItemPreview() {
    ExportEodItem(
        text = "Pick me",
        selected = false,
        enabled = true,
        onClick = {},
        trailingIcon = {
            Icon(
                painter = painterResource(id = BanklyIcons.BoxCheck),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        },
    )
}
