package com.bankly.core.common.ui.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.R
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyOutlinedButton
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
fun TransactionAlertView(
    amount: Double,
    senderAccountName: String,
    onCloseClick: () -> Unit,
    onViewTransactionDetailsClick: () -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = buildAnnotatedString {
            withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
                append(stringResource(R.string.msg_credit_alert_of))
            }
            withStyle(
                MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    .toSpanStyle()
            ) {
                append(Formatter.formatAmount(amount, true))
            }
        })
        Text(text = buildAnnotatedString {
            withStyle(
                MaterialTheme.typography.bodyMedium.toSpanStyle()
            ) {
                append(stringResource(R.string.msg_from))
            }

            withStyle(
                MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    .toSpanStyle()
            ) {
                append(senderAccountName)
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        BanklyFilledButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.action_view_transaction_details),
            onClick = onViewTransactionDetailsClick,
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        BanklyOutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.action_close),
            onClick = onCloseClick,
            textColor = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun TransactionAlertPreview() {
    BanklyTheme {
        TransactionAlertView(
            amount = 200.00,
            senderAccountName = "Hassan Abdulwahab",
            onViewTransactionDetailsClick = {},
            onCloseClick = {}
        )
    }

}