package com.bankly.core.common.ui.transactionfailed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyOutlinedButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme


@Composable
fun TransactionFailedRoute(
    message: String = "",
    onGoToHome: () -> Unit
) {
    TransactionFailedScreen(
        message = message,
        onGoToHome = onGoToHome
    )
}

@Composable
fun  TransactionFailedScreen(
    message: String,
    onGoToHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 120.dp)
                .fillMaxSize()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = BanklyIcons.Failed),
                contentDescription = "Successful Icon"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.msg_transaction_failed),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            BanklyOutlinedButton(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.action_go_to_home),
                onClick = onGoToHome,
                isEnabled = true,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SuccessfulScreenPreview() {
    BanklyTheme {
        TransactionFailedScreen(
            message = stringResource(R.string.msg_transaction_failed),
            onGoToHome = {}
        )
    }
}