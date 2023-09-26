package com.bankly.feature.paywithtransfer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.paywithtransfer.model.TransferAlert

@Composable
internal fun TransferAlertListItem(
    transferAlert: TransferAlert,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painter = painterResource(id = BanklyIcons.Transfer_Inward), contentDescription = null)
        Column {
            Text(
                text = transferAlert.title,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
            )
            Text(
                text = transferAlert.date,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
            )
        }
        Column {
            Text(
                text = transferAlert.amount,
                style = MaterialTheme.typography.bodySmall.copy(color = BanklySuccessColor.successColor),
            )
            Text(
                text = transferAlert.balance,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TransferAlertListItemPreview() {
    BanklyTheme {
        TransferAlertListItem(
            transferAlert = TransferAlert.mock().first(),
        )
    }
}
