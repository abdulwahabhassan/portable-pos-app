package com.bankly.core.common.ui.processTransaction

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun ProcessTransactionRoute(
    onTransactionProcessed: () -> Unit,
) {
    ProcessTransactionScreen(
        onTransactionProcessed = onTransactionProcessed
    )
}

@Composable
fun ProcessTransactionScreen(
    onTransactionProcessed: () -> Unit
) {
    BackHandler {
        //Override and do nothing
    }
    Scaffold(
        topBar = {
            BanklyTitleBar(
                title = "",
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(50.dp)
                        .size(100.dp)
                        .clickable { onTransactionProcessed() },
                    strokeWidth = 12.dp,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeCap = StrokeCap.Round
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Just a minute, we are reconfirming the status of your transaction.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
            item {
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SelectAccountTypeScreenPreview() {
    BanklyTheme {
        ProcessTransactionScreen(
            onTransactionProcessed = {},
        )
    }
}