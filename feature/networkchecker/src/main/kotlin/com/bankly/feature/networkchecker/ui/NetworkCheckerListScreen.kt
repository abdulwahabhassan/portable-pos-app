package com.bankly.feature.networkchecker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.networkchecker.R
import com.bankly.feature.networkchecker.model.NetworkCheckerTab
import com.bankly.feature.networkchecker.ui.component.BankNetworkSearchableListView

@Composable
internal fun NetworkCheckerListRoute(
    viewModel: NetworkCheckerViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    NetworkCheckerListScreen(
        onBackPress = onBackPress,
        screenState = screenState
    ) { uiEvent: NetworkCheckerScreenEvent ->
        viewModel.sendEvent(uiEvent)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.sendEvent(NetworkCheckerScreenEvent.LoadUiData)
    }
}

@Composable
private fun NetworkCheckerListScreen(
    onBackPress: () -> Unit,
    screenState: NetworkCheckerScreenState,
    onUiEvent: (NetworkCheckerScreenEvent) -> Unit
) {
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
            }
        },
    ) { paddingValues ->
        when(screenState.selectedTab) {
            NetworkCheckerTab.TRANSFERS -> {
                BankNetworkSearchableListView(
                    modifier = Modifier.padding(paddingValues),
                    isBankListLoading = screenState.isBankListLoading,
                    bankList = screenState.bankNetworks,
                )
            }
            NetworkCheckerTab.WITHDRAWALS -> {

            }
        }

    }

    BanklyCenterDialog(
        title = stringResource(R.string.title_error),
        subtitle = screenState.errorDialogMessage,
        positiveActionText = stringResource(R.string.action_dismiss),
        showDialog = screenState.showErrorDialog,
        positiveAction = {
            onUiEvent(NetworkCheckerScreenEvent.DismissErrorDialog)
        },
        onDismissDialog = {
            onUiEvent(NetworkCheckerScreenEvent.DismissErrorDialog)
        }
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun NetworkCheckerListScreenPreview() {
    BanklyTheme {
        NetworkCheckerListScreen(
            onBackPress = {},
            screenState = NetworkCheckerScreenState()
        ) {}
    }
}
