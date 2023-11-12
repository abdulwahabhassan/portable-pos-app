package com.bankly.feature.eod.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
internal fun SyncEodItemCard(
    amount: Double,
    title: String,
    backgroundColor: Color,
    icon: Int,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = Formatter.formatAmount(
                    value = amount,
                    includeNairaSymbol = true,
                    addSpaceBetweenSymbolAndAmount = true
                ),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.surfaceVariant
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SyncEodItemCardPreview() {
    BanklyTheme {
        SyncEodItemCard(
            amount = 0.00,
            title = "Processed",
            backgroundColor = MaterialTheme.colorScheme.primary,
            icon = BanklyIcons.SyncProcessed
        )
    }
}