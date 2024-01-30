package com.bankly.feature.dashboard.ui.transactions

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.model.DateRange
import com.bankly.core.common.model.TransactionCategoryTab
import com.bankly.core.common.ui.view.FilterView
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyDatePicker
import com.bankly.core.designsystem.component.BanklyFilterChip
import com.bankly.core.designsystem.component.BanklySearchBar
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.model.entity.CashFlow
import com.bankly.core.model.entity.TransactionFilter
import com.bankly.core.model.entity.TransactionFilterType
import com.bankly.core.model.sealed.TransactionReceipt
import com.bankly.feature.dashboard.R
import com.bankly.feature.dashboard.ui.component.TransactionListItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@Composable
internal fun TransactionsRoute(
    viewModel: TransactionsViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onGoToTransactionDetailsScreen: (TransactionReceipt) -> Unit,
    updateLoadingStatus: (Boolean) -> Unit,
    onSessionExpired: () -> Unit,
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()

    TransactionsScreen(
        screenState = screenState,
        onBackPress = onBackPress,
        onUiEvent = { uiEvent: TransactionsScreenEvent ->
            viewModel.sendEvent(uiEvent)
        },
        onTransactionSelected = onGoToTransactionDetailsScreen,
    )

    LaunchedEffect(key1 = screenState.isLoading) {
        updateLoadingStatus(screenState.isLoading)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.sendEvent(TransactionsScreenEvent.LoadUiData)
    }
    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach { oneShotState: TransactionsScreenOneShotState ->
            when (oneShotState) {
                TransactionsScreenOneShotState.OnSessionExpired -> onSessionExpired()
            }
        }.launchIn(this)
    })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun TransactionsScreen(
    onBackPress: () -> Unit,
    screenState: TransactionsScreenState,
    onUiEvent: (TransactionsScreenEvent) -> Unit,
    onTransactionSelected: (TransactionReceipt) -> Unit,
) {
    BackHandler {
        onBackPress()
    }

    val context = LocalContext.current
    val filteredList by remember(
        screenState.transactions,
        screenState.searchQuery,
        screenState.selectedCategoryTab,
    ) {
        val resultList = when (screenState.selectedCategoryTab) {
            TransactionCategoryTab.ALL -> screenState.transactions
            TransactionCategoryTab.CREDIT -> screenState.transactions.filter { it.isCreditTransaction }
            TransactionCategoryTab.DEBIT -> screenState.transactions.filter { it.isDebitTransaction }
        }.filter {
            it.transactionAmount.toString()
                .contains(screenState.searchQuery, true) || it.transactionReference.contains(
                screenState.searchQuery, true,
            ) || it.transactionTypeLabel.contains(screenState.searchQuery, true)
        }
        mutableStateOf(resultList)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
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
                            onClick = {
                                coroutineScope.launch {
                                    sheetState.show()
                                }
                            },
                            enabled = screenState.isUserInputEnabled
                        )
                    },
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
                    unselectedTabTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        },

        ) { padding ->

        val pullRefreshState =
            rememberPullRefreshState(refreshing = screenState.isRefreshing, onRefresh = {
                onUiEvent(TransactionsScreenEvent.OnRefresh)
            })

        Log.d("debug", "screen state: ${screenState.copy(transactions = emptyList())}")
        Box(Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (listOf(
                        screenState.selectedTransactionFilterTypes.isNotEmpty(),
                        screenState.accountNameTFV.text.isNotEmpty(),
                        screenState.transactionReferenceTFV.text.isNotEmpty(),
                        screenState.startDateFilter != null,
                        screenState.endDateFilter != null,
                        screenState.cashFlows.any { it.isSelected }
                    ).any { it }
                ) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp),
                        ) {
                            LazyRow(
                                modifier = Modifier
                                    .padding(end = 16.dp, start = 16.dp)
                                    .align(Alignment.CenterStart)
                                    .fillMaxWidth(0.755f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                items(
                                    screenState.selectedTransactionFilterTypes,
                                    key = TransactionFilterType::id,
                                ) { item: TransactionFilterType ->
                                    BanklyFilterChip(
                                        title = item.name,
                                        isSelected = item.isSelected,
                                        onClick = {
                                            onUiEvent(
                                                TransactionsScreenEvent.RemoveTransactionTypeFilterItem(
                                                    item,
                                                ),
                                            )
                                        },
                                        trailingIcon = {
                                            Icon(
                                                painter = painterResource(id = BanklyIcons.Remove),
                                                contentDescription = null,
                                                tint = Color.Unspecified,
                                            )
                                        },
                                    )
                                }
                                if (screenState.transactionReferenceTFV.text.isNotEmpty()) {
                                    item {
                                        BanklyFilterChip(
                                            title = stringResource(
                                                R.string.title_ref,
                                                screenState.transactionReferenceTFV.text
                                            ),
                                            isSelected = true,
                                            onClick = {
                                                onUiEvent(
                                                    TransactionsScreenEvent.RemoveTransactionReferenceItem,
                                                )
                                            },
                                            trailingIcon = {
                                                Icon(
                                                    painter = painterResource(id = BanklyIcons.Remove),
                                                    contentDescription = null,
                                                    tint = Color.Unspecified,
                                                )
                                            },
                                        )
                                    }
                                }
                                if (screenState.accountNameTFV.text.isNotEmpty()) {
                                    item {
                                        BanklyFilterChip(
                                            title = stringResource(
                                                R.string.title_acct_name,
                                                screenState.accountNameTFV.text
                                            ),
                                            isSelected = true,
                                            onClick = {
                                                onUiEvent(
                                                    TransactionsScreenEvent.RemoveAccountNameItem,
                                                )
                                            },
                                            trailingIcon = {
                                                Icon(
                                                    painter = painterResource(id = BanklyIcons.Remove),
                                                    contentDescription = null,
                                                    tint = Color.Unspecified,
                                                )
                                            },
                                        )
                                    }
                                }
                                if (screenState.cashFlows.any { it.isSelected }) {
                                    items(screenState.cashFlows.filter { it.isSelected }) { cashflow: CashFlow ->
                                        BanklyFilterChip(
                                            title = stringResource(
                                                R.string.title_cash_flow,
                                                cashflow.title
                                            ),
                                            isSelected = cashflow.isSelected,
                                            onClick = {
                                                onUiEvent(
                                                    TransactionsScreenEvent.RemoveCashFlowItem(
                                                        cashflow
                                                    ),
                                                )
                                            },
                                            trailingIcon = {
                                                Icon(
                                                    painter = painterResource(id = BanklyIcons.Remove),
                                                    contentDescription = null,
                                                    tint = Color.Unspecified,
                                                )
                                            },
                                        )
                                    }
                                }
                                if (screenState.startDateFilter != null) {
                                    item {
                                        BanklyFilterChip(
                                            title = stringResource(
                                                R.string.title_start_date,
                                                screenState.startDateFilter
                                            ),
                                            isSelected = true,
                                            onClick = {
                                                onUiEvent(
                                                    TransactionsScreenEvent.RemoveDateItem(DateRange.START_DATE),
                                                )
                                            },
                                            trailingIcon = {
                                                Icon(
                                                    painter = painterResource(id = BanklyIcons.Remove),
                                                    contentDescription = null,
                                                    tint = Color.Unspecified,
                                                )
                                            },
                                        )
                                    }
                                }
                                if (screenState.endDateFilter != null) {
                                    item {
                                        BanklyFilterChip(
                                            title = stringResource(
                                                R.string.title_end_date,
                                                screenState.endDateFilter
                                            ),
                                            isSelected = true,
                                            onClick = {
                                                onUiEvent(
                                                    TransactionsScreenEvent.RemoveDateItem(DateRange.END_DATE),
                                                )
                                            },
                                            trailingIcon = {
                                                Icon(
                                                    painter = painterResource(id = BanklyIcons.Remove),
                                                    contentDescription = null,
                                                    tint = Color.Unspecified,
                                                )
                                            },
                                        )
                                    }
                                }
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 16.dp),
                            ) {
                                BanklyClickableText(
                                    text = buildAnnotatedString { append(stringResource(R.string.action_clear_all)) },
                                    onClick = {
                                        onUiEvent(TransactionsScreenEvent.OnClearAllFilters)
                                    },
                                    backgroundShape = RoundedCornerShape(4.dp),
                                    textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
                                )
                            }
                        }
                    }
                }

                items(filteredList) { item ->
                    TransactionListItem(
                        transaction = item,
                        onClick = {
                            onTransactionSelected(item.toTransactionReceipt())
                        },
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = screenState.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.Center),
                contentColor = MaterialTheme.colorScheme.primary,
            )
        }

        if (sheetState.isVisible) {
            ModalBottomSheet(
                modifier = Modifier
                    .focusable()
                    .focusRequester(FocusRequester()),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                dragHandle = { BottomSheetDefaults.DragHandle(width = 80.dp) },
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                sheetState = sheetState,
                onDismissRequest = {
                    onUiEvent(TransactionsScreenEvent.LoadUiData)
                },
                windowInsets = WindowInsets(top = 24.dp, bottom = 50.dp),
            ) {
                FilterView(
                    isTransactionReferenceError = screenState.isTransactionReferenceError,
                    transactionReferenceFeedback = screenState.transactionReferenceFeedBack,
                    transactionReferenceTFV = screenState.transactionReferenceTFV,
                    onEnterTransactionReference = { textFieldValue: TextFieldValue ->
                        onUiEvent(TransactionsScreenEvent.OnInputTransactionReference(textFieldValue))
                    },
                    isUserInputEnabled = screenState.isUserInputEnabled,
                    isAccountNameFeedbackError = screenState.isAccountNameFeedbackError,
                    accountNameFeedback = screenState.accountNameFeedback,
                    accountNameTFV = screenState.accountNameTFV,
                    onEnterAccountName = { textFieldValue: TextFieldValue ->
                        onUiEvent(TransactionsScreenEvent.OnInputAccountName(textFieldValue))
                    },
                    onCashFlowFilterChipClick = { cashFlow: com.bankly.core.model.entity.CashFlow ->
                        onUiEvent(
                            TransactionsScreenEvent.OnCashFlowFilterChipClick(
                                cashFlow,
                                cashFlows = screenState.cashFlows,
                            ),
                        )
                    },
                    shouldShowAllTransactionFilterType = screenState.showAllTransactionFilterType,
                    onShowLessTypesClick = {
                        onUiEvent(TransactionsScreenEvent.OnShowLessFilterTypesClick)
                    },
                    onShowMoreTypesClick = {
                        onUiEvent(TransactionsScreenEvent.OnShowMoreFilterTypesClick)
                    },
                    startDateFilter = screenState.startDateFilter,
                    endDateFilter = screenState.endDateFilter,
                    onStartDateFilterClick = {
                        onUiEvent(TransactionsScreenEvent.DateFilterClick(DateRange.START_DATE))
                    },
                    onEndDateFilterClick = {
                        onUiEvent(TransactionsScreenEvent.DateFilterClick(DateRange.END_DATE))
                    },
                    startDateFilterFeedBack = screenState.startDateFeedBack,
                    endDateFilterFeedBack = screenState.endDateFeedBack,
                    isStartDateFilterError = screenState.isStartDateFilterError,
                    isEndDateFilterError = screenState.isEndDateFilterError,
                    cashFlows = screenState.cashFlows,
                    transactionFilterTypes = screenState.allTransactionFilterTypes,
                    onTransactionFilterTypeSelected = { transactionFilterType ->
                        onUiEvent(
                            TransactionsScreenEvent.OnTransactionFilterTypeSelected(
                                transactionFilterType,
                                screenState.allTransactionFilterTypes,
                            ),
                        )
                    },
                    onApplyClick = { filter: TransactionFilter ->
                        onUiEvent(TransactionsScreenEvent.OnApplyFilterClick(transactionFilter = filter))
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    },
                    onCloseClick = {
                        onUiEvent(TransactionsScreenEvent.LoadUiData)
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    },
                )
            }
        }
    }

    if (screenState.showDatePicker && screenState.whichDateRange != null) {
        BanklyDatePicker(
            context = context,
            onDateSelected = { date: LocalDate ->
                onUiEvent(
                    TransactionsScreenEvent.OnDateSelected(
                        date,
                        screenState.whichDateRange,
                    ),
                )
            },
            onDismissDatePicker = {
                onUiEvent(TransactionsScreenEvent.OnDismissDatePicker)
            },
        )
    }

    BanklyCenterDialog(
        title = stringResource(R.string.title_error),
        subtitle = screenState.errorDialogMessage,
        icon = BanklyIcons.ErrorAlert,
        positiveActionText = stringResource(R.string.action_dismiss),
        positiveAction = {
            onUiEvent(TransactionsScreenEvent.DismissErrorDialog)
        },
        showDialog = screenState.showErrorDialog,
        onDismissDialog = {
            onUiEvent(TransactionsScreenEvent.DismissErrorDialog)
        },
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun TransactionsScreenPreview() {
    BanklyTheme {
        TransactionsScreen(
            onBackPress = { },
            screenState = TransactionsScreenState(
                transactions = listOf(
                    com.bankly.core.model.entity.Transaction.History(
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
                    ),
                ),
            ),
            onUiEvent = {},
            onTransactionSelected = {},
        )
    }
}
