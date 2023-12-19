package com.bankly.feature.dashboard.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.R
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.Transaction
import com.bankly.core.util.Formatter.formatServerDateTime

@Composable
internal fun TransactionListItem(
    transaction: Transaction,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = true,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(
                    id = if (transaction.isCredit) {
                        BanklyIcons.TransferInward
                    } else {
                        BanklyIcons.TransferOutward
                    },
                ),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(
                    text = transaction.transactionTypeName,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = formatServerDateTime(transaction.transactionDate),
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.symbol_plus_sign) + Formatter.formatAmount(
                        transaction.amount,
                        true,
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (transaction.isCredit) {
                            BanklySuccessColor.successColor
                        } else {
                            MaterialTheme.colorScheme.error
                        },
                    ),
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.tertiaryContainer,
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun TransactionListItemPreview() {
    BanklyTheme {
        TransactionListItem(
            transaction = Transaction(
                creditAccountNo = "",
                debitAccountNo = "",
                transactionBy = "",
                channelName = "",
                statusName = "",
                userType = 0,
                userId = 0,
                initiator = "",
                archived = false,
                product = "",
                hasProduct = false,
                senderName = "",
                receiverName = "",
                balanceBeforeTransaction = 0.00,
                leg = 0,
                id = 0,
                reference = "",
                transactionType = 0,
                transactionTypeName = "",
                description = "",
                narration = "",
                amount = 0.00,
                creditAccountNumber = "",
                deditAccountNumber = "",
                parentReference = "",
                transactionDate = "2023-12-16T08:02:31.437",
                credit = 0.00,
                debit = 0.00,
                balanceAfterTransaction = 0.00,
                sender = "",
                receiver = "",
                channel = 0,
                status = 0,
                charges = 0.00,
                aggregatorCommission = 0.00,
                hasCharges = false,
                agentCommission = 0.00,
                debitAccountNumber = "",
                initiatedBy = "",
                stateId = 0,
                lgaId = 0,
                regionId = "",
                aggregatorId = 0,
                isCredit = false,
                isDebit = false,
            ),
            onClick = {},
        )
    }
}
