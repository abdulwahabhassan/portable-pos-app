package com.bankly.feature.dashboard.ui.home

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.Feature
import com.bankly.feature.dashboard.R
import com.bankly.feature.dashboard.ui.component.FeatureCard
import com.bankly.feature.dashboard.ui.component.WalletCard
import com.bankly.kozonpaymentlibrarymodule.helper.ConfigParameters
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun HomeTab(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onFeatureCardClick: (Feature) -> Unit,
    activity: Activity,
    onSessionExpired: () -> Unit,
) {
    val screenState = viewModel.uiState.collectAsStateWithLifecycle().value
    HomeScreen(
        screenState = screenState,
        onUiEvent = { uiEvent: HomeScreenEvent -> viewModel.sendEvent(uiEvent) },
    )
    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendEvent(HomeScreenEvent.FetchWalletBalance)
        ConfigParameters.downloadTmsParams(activity)
    })

    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach { oneShotState: HomeScreenOneShotState ->
            when (oneShotState) {
                HomeScreenOneShotState.OnSessionExpired -> {
                    onSessionExpired()
                }

                is HomeScreenOneShotState.GoToFeature -> {
                    onFeatureCardClick(oneShotState.feature)
                }
            }
        }.launchIn(this)
    })
}

@Composable
internal fun HomeScreen(
    screenState: HomeScreenState,
    onUiEvent: (HomeScreenEvent) -> Unit,
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
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                .align(Alignment.Start),
            text = stringResource(R.string.title_quick_action),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(Feature.values().filter { it.isQuickAction }) { feature: Feature ->
                FeatureCard(
                    feature = feature,
                    onClick = {
                        onUiEvent(HomeScreenEvent.OnFeatureCardClick(feature))
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

    BanklyCenterDialog(
        title = stringResource(id = R.string.title_access_denied),
        subtitle = stringResource(id = R.string.msg_enable_feature_in_settings),
        icon = BanklyIcons.AccessDenied,
        showDialog = screenState.showFeatureAccessDeniedDialog,
        onDismissDialog = {
            onUiEvent(HomeScreenEvent.OnDismissFeatureAccessDeniedDialog)
        },
        showCloseIcon = true,
        extraContent = {
            BanklyFilledButton(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.action_go_to_settings),
                onClick = {
                    onUiEvent(HomeScreenEvent.OnDismissFeatureAccessDeniedDialog)
                    onUiEvent(HomeScreenEvent.OnFeatureCardClick(Feature.Settings()))
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
            )
        },
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun HomeScreenPreview() {
    BanklyTheme {
        HomeScreen(
            screenState = HomeScreenState(),
            onUiEvent = {},
        )
    }
}
