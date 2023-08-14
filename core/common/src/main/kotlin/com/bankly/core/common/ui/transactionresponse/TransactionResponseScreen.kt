package com.bankly.core.common.ui.transactionresponse

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyOutlinedButton
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun TransactionResponseRoute(
    onViewTransactionDetailsClick: () -> Unit,
    onGoHomeClick: () -> Unit,
    title: String,
    subTitle: String,
    icon: Int
) {
    TransactionResponseScreen(
        onViewTransactionDetailsClick = onViewTransactionDetailsClick,
        onGoHomeClick = onGoHomeClick,
        title = title,
        subTitle = subTitle,
        icon = icon
    )
}

@Composable
fun TransactionResponseScreen(
    onViewTransactionDetailsClick: () -> Unit,
    onGoHomeClick: () -> Unit,
    title: String,
    subTitle: String,
    icon: Int
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = subTitle, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(40.dp))
                BanklyFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    text = stringResource(R.string.action_view_transaction_details),
                    onClick = onViewTransactionDetailsClick
                )
                Spacer(modifier = Modifier.height(16.dp))
                BanklyOutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    text = stringResource(R.string.action_go_to_home),
                    onClick = onGoHomeClick,
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TransactionResponseScreenPreview() {
    BanklyTheme {
        TransactionResponseScreen(
            onViewTransactionDetailsClick = {},
            onGoHomeClick = {},
            "Transaction Successful",
            "",
            com.bankly.core.designsystem.R.drawable.ic_successful
        )
    }
}