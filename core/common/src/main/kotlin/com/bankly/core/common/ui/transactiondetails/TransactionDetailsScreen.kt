package com.bankly.core.common.ui.transactiondetails

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bankly.core.common.R
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.component.BanklyDetailRow
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyOutlinedButton
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.sealed.TransactionReceipt

@Composable
fun TransactionDetailsRoute(
    transactionReceipt: TransactionReceipt,
    onShareClick: () -> Unit,
    onSmsClick: (TransactionReceipt) -> Unit,
    onLogComplaintClick: () -> Unit,
    onGoToHomeClick: (() -> Unit)? = null,
    onBackPress: (() -> Unit)? = null
) {
    TransactionDetailsScreen(
        transactionReceipt = transactionReceipt,
        onShareClick = onShareClick,
        onSmsClick = onSmsClick,
        onLogComplaintClick = onLogComplaintClick,
        onGoToHomeClick = onGoToHomeClick,
        onBackPress = onBackPress
    )
}

@Composable
fun TransactionDetailsScreen(
    transactionReceipt: TransactionReceipt,
    onShareClick: () -> Unit,
    onSmsClick: (TransactionReceipt) -> Unit,
    onLogComplaintClick: () -> Unit,
    onGoToHomeClick: (() -> Unit)? = null,
    onBackPress: (() -> Unit)? = null
) {
    if (onBackPress != null) {
       BackHandler {
           onBackPress()
       }
    }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                title = stringResource(R.string.title_transaction_receipt),
                onBackPress = onBackPress
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                            )
                            .padding(vertical = 16.dp, horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = stringResource(R.string.msg_total_amount),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            ),
                        )
                        Text(
                            text = Formatter.formatAmount(transactionReceipt.transactionAmount, true),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        val dottedLineColor = MaterialTheme.colorScheme.onPrimaryContainer
                        Canvas(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                        ) {
                            drawLine(
                                color = dottedLineColor,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        transactionReceipt.toDetailsMap().filter { it.value.isNotEmpty() }
                            .forEach { (label, value) ->
                                BanklyDetailRow(
                                    label = label,
                                    value = value,
                                    labelStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary, letterSpacing = 0.25.sp),
                                    valueStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, letterSpacing = 0.25.sp),
                                )
                            }
                    }

                    Icon(
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(id = BanklyIcons.Successful),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = stringResource(R.string.msg_send_transaction_receipt),
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    )
                    Divider(
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    BanklyOutlinedButton(
                        modifier = Modifier.weight(1f),
                        text = "Share",
                        onClick = onShareClick,
                        backgroundColor = MaterialTheme.colorScheme.background,
                    )
                    BanklyOutlinedButton(
                        modifier = Modifier.weight(1f),
                        text = "SMS",
                        onClick = {
                            onSmsClick(transactionReceipt)
                        },
                        backgroundColor = MaterialTheme.colorScheme.background,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                BanklyFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.action_log_complaint),
                    onClick = onLogComplaintClick,
                    textColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (onGoToHomeClick != null) {
                    BanklyFilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.action_go_to_home),
                        onClick = onGoToHomeClick,
                    )
                }
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
            transactionReceipt = TransactionReceipt.BankTransfer(
                "Hassan Abdulwahab",
                "0428295437",
                "GTBANK",
                100.00,
                "177282",
                "08123939291",
                1,
                18,
                "Transfer Completed Successfully",
                "0428094437",
                "Main",
                "2023-08-15T21:14:40.5225813Z",
                "", "Successful",
            ),
            onShareClick = {},
            onSmsClick = {},
            onLogComplaintClick = {},
            onGoToHomeClick = {},
            onBackPress = {}
        )
    }
}
