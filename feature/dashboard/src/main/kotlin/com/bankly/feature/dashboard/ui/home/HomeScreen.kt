package com.bankly.feature.dashboard.ui.home

import android.app.Activity
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.dashboard.R
import com.bankly.core.entity.Feature
import com.bankly.feature.dashboard.ui.component.FeatureCard
import com.bankly.feature.dashboard.ui.component.WalletCard
import com.bankly.kozonpaymentlibrarymodule.helper.ConfigParameters

@Composable
internal fun HomeTab(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onFeatureCardClick: (Feature) -> Unit,
    activity: Activity
) {
    val screenState = viewModel.uiState.collectAsStateWithLifecycle().value
    HomeScreen(
        screenState = screenState,
        onUiEvent = { uiEvent: HomeScreenEvent -> viewModel.sendEvent(uiEvent) },
        onFeatureCardClick = onFeatureCardClick,
    )
    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendEvent(HomeScreenEvent.FetchWalletBalance)
        ConfigParameters.downloadTmsParams(activity)
    })
}

@Composable
internal fun HomeScreen(
    screenState: HomeScreenState,
    onUiEvent: (HomeScreenEvent) -> Unit,
    onFeatureCardClick: (Feature) -> Unit,
) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),

        ) {
            WalletCard(
                shouldShowWalletBalance = screenState.shouldShowWalletBalance,
                onToggleWalletBalanceVisibility = { toggleState ->
                    onUiEvent(HomeScreenEvent.ToggleWalletBalanceVisibility(toggleState))
                },
                accountNumber = screenState.accountNumber,
                bankName = screenState.bankName,
                currentBalance = screenState.accountBalance,
                shouldShowVisibilityIcon = screenState.shouldShowVisibilityIcon,
                isWalletBalanceLoading = screenState.shouldShowLoadingIcon,
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
            items(Feature.values().filter { it.isQuickAction }) { feature: Feature ->
                FeatureCard(
                    feature = feature,
                    onClick = {
                        onFeatureCardClick(feature)
                    },
                )
            }
        }
    }
    if (screenState.shouldShowErrorDialog) {
        BanklyActionDialog(
            title = stringResource(R.string.error),
            subtitle = screenState.message,
            positiveActionText = "Dismiss",
            positiveAction = {
                onUiEvent(HomeScreenEvent.OnDismissErrorDialog)
            },
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun HomeScreenPreview() {
    BanklyTheme {
        HomeScreen(
            screenState = HomeScreenState(),
            onUiEvent = {},
            onFeatureCardClick = {},
        )
    }
}
