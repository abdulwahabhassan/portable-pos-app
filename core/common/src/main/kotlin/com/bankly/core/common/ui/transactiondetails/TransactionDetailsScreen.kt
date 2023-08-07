package com.bankly.core.common.ui.transactiondetails

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyOutlinedButton
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.model.TransactionDetails
import com.bankly.core.model.toDetailsMap

@Composable
fun TransactionDetailsRoute(
    onShareClick: () -> Unit,
    onSmsClick: () -> Unit,
    onLogComplaintClick: () -> Unit
) {
    TransactionDetailsScreen(
        onShareClick,
        onSmsClick,
        onLogComplaintClick
    )
}
@Composable
fun TransactionDetailsScreen(
    onShareClick: () -> Unit,
    onSmsClick: () -> Unit,
    onLogComplaintClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        topBar = {
            BanklyTitleBar(
                title = "Transaction Receipt",
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                            .padding(vertical = 16.dp, horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Total Amount",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                        Text(text = "₦20,000.00", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        val dottedLineColor = MaterialTheme.colorScheme.onPrimaryContainer
                        Canvas(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        ) {
                            drawLine(
                                color = dottedLineColor,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        TransactionDetails(
                            "Card Payment",
                            "Successful",
                            "Approved",
                            "P2683003172",
                            "MasterCard",
                            "Josh Osazuwa",
                            "8938387373820351",
                            "04-05-2023 12:34:43",
                            "00",
                            "67237882",
                            "8987",
                            "₦20,000.00",
                            ""
                        ).toDetailsMap().filter { it.value.isNotEmpty() }
                            .forEach { (label, value) ->
                                DetailRow(label = label, value = value)
                            }
                    }

                    Icon(
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(id = BanklyIcons.Successful),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Divider(
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Send Transaction Receipt",
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                    Divider(
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BanklyOutlinedButton(
                        modifier = Modifier.weight(1f),
                        text = "Share",
                        onClick = onShareClick,
                        backgroundColor = MaterialTheme.colorScheme.background
                    )
                    BanklyOutlinedButton(
                        modifier = Modifier.weight(1f),
                        text = "SMS",
                        onClick = onSmsClick,
                        backgroundColor = MaterialTheme.colorScheme.background
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                BanklyFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Log Complaint",
                    onClick = onLogComplaintClick,
                    textColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TransactionDetailsScreenPreview() {
    BanklyTheme {
        TransactionDetailsScreen(
            {},
            {},
            {}
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.End
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DetailRowPreview() {
    BanklyTheme {
        DetailRow("Transaction Type", "Card Payment")
    }
}

