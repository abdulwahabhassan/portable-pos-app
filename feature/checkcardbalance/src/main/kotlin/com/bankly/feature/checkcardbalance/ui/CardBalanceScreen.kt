package com.bankly.feature.checkcardbalance.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
fun CardBalanceRoute(
    amount: String,
    onGoToDashboardClick: () -> Unit,
) {
    CardBalanceScreen(
        onDoneClick = onGoToDashboardClick,
        amount = amount
    )
}

@Composable
fun CardBalanceScreen(
    onDoneClick: () -> Unit,
    amount: String,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Box(Modifier)
        }
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Icon(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = BanklyIcons.Successful),
                    contentDescription = "Successful Icon",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Balance Retrieved Successfully",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Medium,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clip(MaterialTheme.shapes.medium)
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Account Balance",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = Formatter.formatAmount(
                        value = amount,
                        includeNairaSymbol = true,
                        addSpaceBetweenSymbolAndAmount = true
                    ),
                    style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
        item {
            BanklyFilledButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .fillMaxWidth(),
                text = "Go to Dashboard",
                onClick = onDoneClick,
                isEnabled = true,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
fun CardBalanceScreenPreview() {
    BanklyTheme {
        CardBalanceScreen(
            onDoneClick = {},
            amount = "200.00"
        )
    }
}
