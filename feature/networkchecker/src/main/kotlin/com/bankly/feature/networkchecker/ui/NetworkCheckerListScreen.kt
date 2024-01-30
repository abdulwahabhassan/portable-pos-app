package com.bankly.feature.networkchecker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.ui.view.ComingSoonView
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklySearchBar
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.networkchecker.R
import com.bankly.feature.networkchecker.model.NetworkCheckerTab
import com.bankly.feature.networkchecker.ui.component.BankNetworkSearchableListView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun NetworkCheckerListRoute(
    viewModel: NetworkCheckerViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    NetworkCheckerListScreen(
        onBackPress = onBackPress,
        screenState = screenState,
    ) { uiEvent: NetworkCheckerScreenEvent ->
        viewModel.sendEvent(uiEvent)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.sendEvent(NetworkCheckerScreenEvent.LoadUiData)
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach { oneShotState: NetworkCheckerScreenOneShotState ->
            when (oneShotState) {
                NetworkCheckerScreenOneShotState.OnSessionExpired -> {
                    onSessionExpired()
                }
            }
        }.launchIn(this)
    })
}

@Composable
private fun NetworkCheckerListScreen(
    onBackPress: () -> Unit,
    screenState: NetworkCheckerScreenState,
    onUiEvent: (NetworkCheckerScreenEvent) -> Unit,
) {
    val bankNetworkList by remember(
        screenState.transfersBankNetworkList,
        screenState.withdrawalBankNetworkList,
        screenState.transfersSearchQuery,
        screenState.withdrawalsSearchQuery,
        screenState.selectedTab,
    ) {
        val resultList = when (screenState.selectedTab) {
            NetworkCheckerTab.TRANSFERS -> screenState.transfersBankNetworkList.filter {
                it.bankName.contains(screenState.transfersSearchQuery, true)
            }

            NetworkCheckerTab.WITHDRAWALS -> screenState.withdrawalBankNetworkList.filter {
                it.bankName.contains(screenState.withdrawalsSearchQuery, true)
            }
        }
        mutableStateOf(resultList)
    }

    Scaffold(
        topBar = {
            Column {
                BanklyTitleBar(
                    isLoading = screenState.isLoading,
                    onBackPress = onBackPress,
                    title = stringResource(R.string.network_check),
                )
                Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 2.dp)) {
                    BanklyTabBar(
                        tabs = NetworkCheckerTab.values().toList(),
                        onTabClick = { tab ->
                            onUiEvent(NetworkCheckerScreenEvent.OnTabSelected(tab))
                        },
                        selectedTab = screenState.selectedTab,
                        selectedTabColor = MaterialTheme.colorScheme.surfaceVariant,
                        selectedTabTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTabTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        rippleColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
                if (screenState.selectedTab != NetworkCheckerTab.WITHDRAWALS && bankNetworkList.isNotEmpty()) {
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    BanklySearchBar(
                        modifier = Modifier,
                        query = when (screenState.selectedTab) {
                            NetworkCheckerTab.TRANSFERS -> screenState.transfersSearchQuery
                            NetworkCheckerTab.WITHDRAWALS -> screenState.withdrawalsSearchQuery
                        },
                        onQueryChange = { query: String ->
                            onUiEvent(
                                NetworkCheckerScreenEvent.OnInputSearchQuery(
                                    query = query,
                                    selectedTab = screenState.selectedTab
                                )
                            )
                        },
                        searchPlaceholder = stringResource(R.string.msg_search_bank),
                    )
                }
            }
        },
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (screenState.selectedTab) {
                NetworkCheckerTab.TRANSFERS -> {
                    BankNetworkSearchableListView(
                        modifier = Modifier.fillMaxSize(),
                        isBankListLoading = screenState.isTransfersBankNetworkListLoading,
                        bankList = bankNetworkList,
                    )
                }

                NetworkCheckerTab.WITHDRAWALS -> {
                    ComingSoonView(
                        modifier = Modifier
                    )
                }
            }
        }
    }

    BanklyCenterDialog(
        title = stringResource(R.string.title_error),
        subtitle = screenState.errorDialogMessage,
        icon = BanklyIcons.ErrorAlert,
        positiveActionText = stringResource(R.string.action_dismiss),
        showDialog = screenState.showErrorDialog,
        positiveAction = {
            onUiEvent(NetworkCheckerScreenEvent.DismissErrorDialog)
        },
        onDismissDialog = {
            onUiEvent(NetworkCheckerScreenEvent.DismissErrorDialog)
        },
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white, heightDp = 600)
private fun NetworkCheckerListScreenPreview() {
    BanklyTheme {
        NetworkCheckerListScreen(
            onBackPress = {},
            screenState = NetworkCheckerScreenState(),
        ) {}
    }
}
