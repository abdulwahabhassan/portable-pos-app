package com.bankly.feature.eod.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
internal fun EodTotalAmountItem(
    amount: Double,
    title: String,
    amountColor: Color = MaterialTheme.colorScheme.tertiary,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.medium,
            )
            .clip(MaterialTheme.shapes.medium)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Text(
            text = Formatter.formatAmount(
                value = amount,
                includeNairaSymbol = true,
                addSpaceBetweenSymbolAndAmount = true,
            ),
            style = MaterialTheme.typography.headlineSmall.copy(color = amountColor),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun SupportListItemPreview() {
    BanklyTheme {
        EodTotalAmountItem(
            65.50,
            "Total Transaction",
        )
    }
}
