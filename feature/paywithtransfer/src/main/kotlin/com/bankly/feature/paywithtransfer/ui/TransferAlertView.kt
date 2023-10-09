package com.bankly.feature.paywithtransfer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyOutlinedButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.paywithtransfer.model.TransferAlert

@Composable
internal fun TransferAlertView(
    transferAlert: TransferAlert,
    onViewTransactionDetailsClick: (TransactionReceipt.PayWithTransfer) -> Unit,
    onGoToHomeClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = BanklyIcons.Successful),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Transfer Alert",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                text = buildAnnotatedString {
                    append("Credit Alert of")
                    append(" ")
                    withStyle(
                        MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            .toSpanStyle()
                    ) {
                        append(Formatter.formatAmount(
                            value = transferAlert.amount,
                            includeNairaSymbol = true
                        ))
                    }
                    append("\n")
                    append("from")
                    append(" ")
                    withStyle(
                        MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            .toSpanStyle()
                    ) {
                        append(transferAlert.sender)
                    }
                })
            Spacer(modifier = Modifier.height(24.dp))
            BanklyFilledButton(
                modifier = Modifier.fillMaxWidth(),
                text = "View Transaction Details",
                onClick = {
                    onViewTransactionDetailsClick(transferAlert.toTransactionReceipt())
                })
            Spacer(modifier = Modifier.height(16.dp))
            BanklyOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Go To Home",
                onClick = onGoToHomeClick)
        }
    }

}

@Preview(showBackground = true, backgroundColor = PreviewColor.white)
@Composable
private fun TransferAlertPreview() {
    BanklyTheme {
        TransferAlertView(
            TransferAlert.mock().first(),
            onViewTransactionDetailsClick = {},
            onGoToHomeClick = {})
    }
}