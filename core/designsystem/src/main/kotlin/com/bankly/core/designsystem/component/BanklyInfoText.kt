package com.bankly.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
fun BanklyInfoText(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = BanklyIcons.Info),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
fun BanklyInfoTextPreview() {
    BanklyTheme {
        BanklyInfoText("This transaction will attract a service charge")
    }
}
