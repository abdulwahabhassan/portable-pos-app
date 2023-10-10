package com.bankly.feature.paywithtransfer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.ui.view.EmptyStateView
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.AgentAccountDetails
import com.bankly.core.entity.RecentFund
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.paywithtransfer.R
import com.bankly.feature.paywithtransfer.ui.component.RecentFundListItem

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
                isLoading = screenState.isLoading,
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
                screenState.agentAccountDetails?.let {
                    AccountDetailsView(
                        isLoading = screenState.isAgentAccountDetailsLoading,
                        accountDetails = it,
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
            }
            if (screenState.isAccountDetailsExpanded.not()) {
                items(screenState.recentFunds) { item: RecentFund ->
                    RecentFundListItem(
                        recentFund = item,
                        onClick = {
                            onUiEvent(PayWithTransferScreenEvent.OnRecentFundSelected(item))
                        }
                    )
                }
            }
        }
    }

    if (screenState.showRecentFundDialog && screenState.selectedRecentFund != null) {
        Dialog(
            onDismissRequest = {
                onUiEvent(PayWithTransferScreenEvent.CloseRecentFundSummaryDialog)
            },
            content = {
                RecentFundSummaryView(
                    screenState.selectedRecentFund,
                    onViewTransactionDetailsClick = onViewTransactionDetailsClick,
                    onGoToHomeClick = onGoToHomeClick,
                    onCloseIconClick = {
                        onUiEvent(PayWithTransferScreenEvent.CloseRecentFundSummaryDialog)
                    }
                )
            }
        )
    }

    if (screenState.showErrorDialog) {
        BanklyActionDialog(
            title = stringResource(R.string.title_error),
            subtitle = screenState.errorDialogMessage,
            positiveActionText = stringResource(R.string.action_dismiss),
            positiveAction = {
                onUiEvent(PayWithTransferScreenEvent.DismissErrorDialog)
            }
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun PayWithTransferScreenPreview() {
    BanklyTheme {
        PayWithTransferScreen(
            onBackPress = { },
            screenState = PayWithTransferScreenState(
                showRecentFundDialog = false,
                selectedRecentFund = RecentFund(
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
                )
            ),
            onUiEvent = {},
            onViewTransactionDetailsClick = {},
            onGoToHomeClick = {}
        )
    }
}
