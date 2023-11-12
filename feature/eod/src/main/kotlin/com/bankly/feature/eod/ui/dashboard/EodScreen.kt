package com.bankly.feature.eod.ui.dashboard

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyErrorColor
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.eod.R
import com.bankly.feature.eod.ui.component.EodActionListItem
import com.bankly.feature.eod.ui.component.EodTotalAmountItem
import com.bankly.feature.eod.model.EodAction
import com.bankly.feature.eod.ui.eodtransactions.ExportEodView
import kotlinx.coroutines.launch

@Composable
internal fun EodRoute(
    viewModel: EodDashboardViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onSyncEodClick: () -> Unit,
    onViewEodTransactionsClick: () -> Unit,
    onExportFullEodClick: () -> Unit
) {
    val screenState by viewModel.uiState.collectAsState()
    EodScreen(
        screenState = screenState,
        onBackPress = onBackPress,
        onSyncEodClick = onSyncEodClick,
        onViewEodTransactionsClick = onViewEodTransactionsClick,
        onExportFullEodClick = onExportFullEodClick
    )
    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.sendEvent(EodDashboardScreenEvent.LoadUiData)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EodScreen(
    screenState: EodDashboardScreenState,
    onBackPress: () -> Unit,
    onSyncEodClick: () -> Unit,
    onViewEodTransactionsClick: () -> Unit,
    onExportFullEodClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { sheetValue: SheetValue ->
            sheetValue != SheetValue.Expanded
        }
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            BanklyTitleBar(
                isLoading = false,
                onBackPress = onBackPress,
                title = stringResource(R.string.title_end_of_day),
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                EodTotalAmountItem(
                    amount = screenState.totalTransactionAmount,
                    title = "Total Transaction",
                    amountColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                )
                EodTotalAmountItem(
                    amount = screenState.totalSuccessfulTransactionAmount,
                    title = "Total Successful",
                    amountColor = BanklySuccessColor.successColor,
                    backgroundColor = BanklySuccessColor.successBackgroundColor
                )
                EodTotalAmountItem(
                    amount = screenState.totalFailedTransactionAmount,
                    title = "Total Failed",
                    amountColor = BanklyErrorColor.errorColor,
                    backgroundColor = BanklyErrorColor.errorBackgroundColor
                )
            }
            items(EodAction.values()) { eodAction: EodAction ->
                EodActionListItem(
                    eodAction = eodAction,
                    onClick = {
                        when (eodAction) {
                            EodAction.SYNC_EOD -> onSyncEodClick()
                            EodAction.VIEW_EOD_TRANSACTIONS -> onViewEodTransactionsClick()
                            EodAction.EXPORT_FULL_EOD -> {
                                coroutineScope.launch {
                                    sheetState.show()
                                }
                            }
                        }
                    }
                )
            }

            item {
                BanklyFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    text = "Share EOD",
                    onClick = {

                    }
                )
            }
        }
        if (sheetState.isVisible) {
            ModalBottomSheet(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                dragHandle = { BottomSheetDefaults.DragHandle(width = 80.dp) },
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                sheetState = sheetState,
                onDismissRequest = { },
                windowInsets = WindowInsets(top = 24.dp)
            ) {
                ExportEodView {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun EodScreenPreview() {
    BanklyTheme {
        EodScreen(
            screenState = EodDashboardScreenState(),
            onBackPress = {},
            onSyncEodClick = {},
            onViewEodTransactionsClick = {},
            onExportFullEodClick = {}
        )
    }
}
