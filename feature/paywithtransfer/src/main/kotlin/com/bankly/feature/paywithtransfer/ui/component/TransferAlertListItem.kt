package com.bankly.feature.paywithtransfer.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.paywithtransfer.R
import com.bankly.feature.paywithtransfer.model.TransferAlert

@Composable
internal fun TransferAlertListItem(
    transferAlert: TransferAlert,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = BanklyIcons.Transfer_Inward),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(
                    text = transferAlert.title,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),

                )
                Text(
                    text = transferAlert.date,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.symbol_plus_sign) +  Formatter.formatAmount(transferAlert.amount, true),
                    style = MaterialTheme.typography.bodyMedium.copy(color = BanklySuccessColor.successColor),
                )
                Text(
                    text = transferAlert.balance,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
                )
            }
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.tertiaryContainer)
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.background)
private fun TransferAlertListItemPreview() {
    BanklyTheme {
        TransferAlertListItem(
            transferAlert = TransferAlert.mock().first(),
            onClick = {}
        )
    }
}
