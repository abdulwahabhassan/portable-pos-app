package com.bankly.core.common.ui.processtransaction

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bankly.core.common.R
import com.bankly.core.common.model.TransactionData
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ProcessTransactionRoute(
    viewModel: ProcessTransactionViewModel = hiltViewModel(),
    transactionData: TransactionData,
    cardTransactionReceipt: TransactionReceipt? = null,
    onTransactionSuccess: (TransactionReceipt) -> Unit,
    onFailedTransaction: (String, TransactionReceipt?) -> Unit,
    onSessionExpired: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    ProcessTransactionScreen()

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            viewModel.oneShotState.collectLatest { oneShotUiState ->
                when (oneShotUiState) {
                    is ProcessTransactionScreenOneShotState.GoToTransactionFailedScreen -> {
                        onFailedTransaction(oneShotUiState.message, cardTransactionReceipt)
                    }
                    is ProcessTransactionScreenOneShotState.GoToTransactionSuccessScreen -> {
                        onTransactionSuccess(oneShotUiState.transactionReceipt)
                    }

                    ProcessTransactionScreenOneShotState.OnSessionExpired -> {
                        onSessionExpired()
                    }
                }
            }
        }
        viewModel.sendEvent(ProcessTransactionScreenEvent.ProcessTransaction(transactionData = transactionData))
    }
}

@Composable
fun ProcessTransactionScreen() {
    BackHandler {
        // Override and do nothing to prevent user from interrupting the transaction
    }
    Scaffold(
        topBar = {
            BanklyTitleBar(
                title = "",
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(50.dp)
                        .size(100.dp),
                    strokeWidth = 12.dp,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeCap = StrokeCap.Round,
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(24.dp),
                    text = stringResource(R.string.msg_just_a_second_we_are_processing_your_transaction),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
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
        ProcessTransactionScreen()
    }
}
