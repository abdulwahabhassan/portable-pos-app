package com.bankly.feature.paywithtransfer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.paywithtransfer.R
import com.bankly.feature.paywithtransfer.model.TransferAlert
import com.bankly.feature.paywithtransfer.ui.component.TransferAlertListItem

@Composable
internal fun PayWithTransferRoute(
    viewModel: PayWithTransferViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt.PayWithTransfer) -> Unit,
    onGoToHomeClick: () -> Unit
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    PayWithTransferScreen(
        screenState = screenState,
        onBackPress = onBackPress,
        onUiEvent = { uiEvent: PayWithTransferScreenEvent ->
            viewModel.sendEvent(uiEvent)
        },
        onViewTransactionDetailsClick = onViewTransactionDetailsClick,
        onGoToHomeClick = onGoToHomeClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PayWithTransferScreen(
    onBackPress: () -> Unit,
    screenState: PayWithTransferScreenState,
    onUiEvent: (PayWithTransferScreenEvent) -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt.PayWithTransfer) -> Unit,
    onGoToHomeClick: () -> Unit
) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.pay_with_transfer),
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            stickyHeader {
                AccountDetailsView(
                    onBackPress = onBackPress,
                    isExpanded = screenState.isAccountDetailsExpanded,
                    onExpandIconClick = { currentState ->
                        onUiEvent(
                            PayWithTransferScreenEvent.OnAccountDetailsExpandButtonClick(
                                currentState
                            )
                        )
                    })
            }
            if (screenState.isAccountDetailsExpanded.not()) {
                items(TransferAlert.mock()) { item: TransferAlert ->
                    TransferAlertListItem(
                        transferAlert = item,
                        onClick = {
                            onUiEvent(PayWithTransferScreenEvent.OnCreditAlertSelected(item))
                        }
                    )
                }
            }
        }
    }

    if (screenState.showTransferAlertDialog && screenState.selectedTransferAlert != null) {
        Dialog(
            onDismissRequest = {
                onUiEvent(PayWithTransferScreenEvent.CloseTransferAlertDialog)
            },
            content = {
                TransferAlertView(
                    screenState.selectedTransferAlert,
                    onViewTransactionDetailsClick = onViewTransactionDetailsClick,
                    onGoToHomeClick = onGoToHomeClick
                )
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PayWithTransferScreenPreview() {
    BanklyTheme {
        PayWithTransferScreen(
            onBackPress = { },
            screenState = PayWithTransferScreenState(
                showTransferAlertDialog = true,
                selectedTransferAlert = TransferAlert.mock().first()
            ),
            onUiEvent = {},
            onViewTransactionDetailsClick = {},
            onGoToHomeClick = {}
        )
    }
}
