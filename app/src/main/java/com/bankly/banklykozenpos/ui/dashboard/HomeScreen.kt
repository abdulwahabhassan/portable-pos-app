package com.bankly.banklykozenpos.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
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
import com.bankly.banklykozenpos.R
import com.bankly.banklykozenpos.model.QuickAction
import com.bankly.core.common.util.Formatter
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val homeScreenUiState = viewModel.state.collectAsStateWithLifecycle().value

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)

        ) {
            WalletCard(
                shouldShowWalletBalance = homeScreenUiState.shouldShowWalletBalance,
                onToggleWalletBalanceVisibility = { toggleState ->
                    viewModel.sendEvent(HomeUiEvent.ToggleWalletBalanceVisibilityEvent(toggleState))
                },
                accountNumber = homeScreenUiState.accountNumber,
                bankName = homeScreenUiState.bankName,
                currentBalance = homeScreenUiState.accountBalance,
                shouldShowVisibilityIcon = homeScreenUiState.shouldShowVisibilityIcon,
                isWalletBalanceLoading = homeScreenUiState.shouldShowLoadingIcon
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.Start),
            text = stringResource(R.string.title_quick_action),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
            items(QuickAction.values()) { quickAction: QuickAction ->
                QuickActionCard(quickAction = quickAction, onClick = {

                })
            }
        }
    }

    if (homeScreenUiState.shouldShowErrorDialog) {
        BanklyActionDialog(
            title = stringResource(R.string.error),
            subtitle = homeScreenUiState.message,
            positiveActionText = "Dismiss",
            positiveAction = {
                viewModel.sendEvent(HomeUiEvent.DismissErrorDialog)
            }
        )
    }
}

@Composable
@Preview(
    showBackground = true
)
private fun HomeScreenPreview() {
    BanklyTheme {
        HomeScreen()
    }
}