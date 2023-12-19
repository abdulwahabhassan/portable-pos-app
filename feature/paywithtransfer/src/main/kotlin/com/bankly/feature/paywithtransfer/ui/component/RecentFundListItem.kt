package com.bankly.feature.paywithtransfer.ui.component

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
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.RecentFund
import com.bankly.core.util.Formatter.formatServerDateTime
import com.bankly.feature.paywithtransfer.R

@Composable
internal fun RecentFundListItem(
    recentFund: RecentFund,
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
                painter = painterResource(id = BanklyIcons.TransferInward),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(
                    text = recentFund.paymentDescription,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = formatServerDateTime(recentFund.transactionDate),
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.symbol_plus_sign) + Formatter.formatAmount(
                        recentFund.amount,
                        true,
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(color = BanklySuccessColor.successColor),
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
private fun RecentFundListItemPreview() {
    BanklyTheme {
        RecentFundListItem(
            recentFund = RecentFund(
                transactionReference = "389030022838200",
                amount = 20.00,
                accountReference = "73783899",
                paymentDescription = "Transfer from Mate",
                transactionHash = "02993920302",
                senderAccountNumber = "637820102",
                senderAccountName = "Mate Blake",
                sessionId = "12436810229",
                phoneNumber = "0812345678",
                userId = "0020020002",
                transactionDate = "2020-12-16T08:02:31.437",
                senderBankName = "Bankly MFB",
                receiverBankName = "Bankly MFB",
                receiverAccountNumber = "3000291002",
                receiverAccountName = "John Doe",
            ),
            onClick = {},
        )
    }
}
