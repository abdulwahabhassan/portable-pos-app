package com.bankly.feature.dashboard.ui.transactions

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.ui.view.EmptyStateView
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklySearchBar
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.Transaction
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.dashboard.R
import com.bankly.feature.dashboard.model.TransactionCategoryTab
import com.bankly.feature.dashboard.ui.component.TransactionListItem
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun TransactionsRoute(
    viewModel: TransactionsViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onGoToTransactionDetailsScreen: (TransactionReceipt) -> Unit,
    updateLoadingStatus: (Boolean) -> Unit
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    TransactionsScreen(
        screenState = screenState,
        onBackPress = onBackPress,
        onUiEvent = { uiEvent: TransactionsScreenEvent ->
            viewModel.sendEvent(uiEvent)
        },
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.oneShotState.collectLatest { oneShotState ->
            when (oneShotState) {
                is TransactionsScreenOneShotState.GoToTransactionDetailsScreen -> {
                    onGoToTransactionDetailsScreen(oneShotState.transaction)
                }

                is TransactionsScreenOneShotState.UpdateLoadingIndicator -> {
                    updateLoadingStatus(oneShotState.isLoading)
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendEvent(TransactionsScreenEvent.FetchTransactions)
    })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsScreen(
    onBackPress: () -> Unit,
    screenState: TransactionsScreenState,
    onUiEvent: (TransactionsScreenEvent) -> Unit,
) {
    BackHandler {
        onBackPress()
    }

    val filteredList by remember(
        screenState.transactions,
        screenState.searchQuery,
        screenState.selectedCategoryTab
    ) {
        val resultList = when (screenState.selectedCategoryTab) {
            TransactionCategoryTab.ALL -> screenState.transactions
            TransactionCategoryTab.CREDIT -> screenState.transactions.filter { it.isCredit }
            TransactionCategoryTab.DEBIT -> screenState.transactions.filter { it.isDebit }
        }.filter {
            it.amount.toString().contains(screenState.searchQuery, true) || it.reference.contains(
                screenState.searchQuery, true
            ) || it.transactionTypeName.contains(screenState.searchQuery, true)
        }
        mutableStateOf(resultList)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        stickyHeader {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                BanklySearchBar(
                    modifier = Modifier,
                    query = screenState.searchQuery,
                    onQueryChange = { query: String ->
                        onUiEvent(TransactionsScreenEvent.OnInputSearchQuery(query))
                    },
                    searchPlaceholder = "Search reference, amount",
                    trailingIcon = {
                        BanklyClickableIcon(
                            modifier = Modifier.padding(end = 16.dp),
                            icon = BanklyIcons.Filter,
                            onClick = { /*TODO*/ })
                    }
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))
                BanklyTabBar(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    tabs = TransactionCategoryTab.values().toList(),
                    onTabClick = { categoryTab ->
                        onUiEvent(TransactionsScreenEvent.OnCategoryTabSelected(categoryTab))
                    },
                    selectedTab = screenState.selectedCategoryTab,
                    selectedTabColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTabTextColor = when (screenState.selectedCategoryTab) {
                        TransactionCategoryTab.ALL -> MaterialTheme.colorScheme.primary
                        TransactionCategoryTab.CREDIT -> BanklySuccessColor.successColor
                        TransactionCategoryTab.DEBIT -> MaterialTheme.colorScheme.error
                    },
                    unselectedTabTextColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        items(filteredList) { item ->
            TransactionListItem(
                transaction = item,
                onClick = {
                    onUiEvent(TransactionsScreenEvent.OnTransactionSelected(item))
                },
            )
        }
    }

    if (screenState.showErrorDialog) {
        BanklyActionDialog(
            title = stringResource(R.string.title_error),
            subtitle = screenState.errorDialogMessage,
            positiveActionText = stringResource(R.string.action_dismiss),
            positiveAction = {
                onUiEvent(TransactionsScreenEvent.DismissErrorDialog)
            }
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun TransactionsScreenPreview() {
    BanklyTheme {
        TransactionsScreen(
            onBackPress = { },
            screenState = TransactionsScreenState(
                transactions = listOf(
                    Transaction(
                        creditAccountNo = "89900322",
                        debitAccountNo = "30003020",
                        transactionBy = "Al Eko",
                        channelName = "393",
                        statusName = "Success",
                        userType = 0,
                        userId = 0,
                        initiator = "Al Hassan",
                        archived = false,
                        product = "",
                        hasProduct = false,
                        senderName = "Al Mana",
                        receiverName = "Al Decko",
                        balanceBeforeTransaction = 0.00,
                        leg = 0,
                        id = 0,
                        reference = "2390202002",
                        transactionType = 0,
                        transactionTypeName = "Transfer",
                        description = "Transfer of 100 Naiara from Al Mana",
                        narration = "Transfer of 100 Naira",
                        amount = 0.00,
                        creditAccountNumber = "7900200201",
                        deditAccountNumber = "039991200230",
                        parentReference = "",
                        transactionDate = "",
                        credit = 0.00,
                        debit = 0.00,
                        balanceAfterTransaction = 0.00,
                        sender = "Al Al Dinho",
                        receiver = "Al Mecko",
                        channel = 0,
                        status = 0,
                        charges = 0.00,
                        aggregatorCommission = 0.00,
                        hasCharges = false,
                        agentCommission = 0.00,
                        debitAccountNumber = "8829923",
                        initiatedBy = "Al Quadri",
                        stateId = 0,
                        lgaId = 0,
                        regionId = "",
                        aggregatorId = 0,
                        isCredit = false,
                        isDebit = true,
                    )
                )
            ),
            onUiEvent = {},
        )
    }
}
