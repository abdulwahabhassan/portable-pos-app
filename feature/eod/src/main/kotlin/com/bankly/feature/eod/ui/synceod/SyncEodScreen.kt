package com.bankly.feature.eod.ui.synceod

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.eod.R
import com.bankly.feature.eod.ui.component.SyncEodItemCard
import com.bankly.feature.eod.ui.dashboard.EodDashboardScreenEvent


@Composable
internal fun SyncEodRoute(
    viewModel: SyncEodViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    SyncEodScreen(
        screenState = screenState,
        onBackPress = onBackPress,
        onUiEvent = { event: SyncEodScreenEvent ->
            viewModel.sendEvent(event)
        }
    )
    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.sendEvent(SyncEodScreenEvent.LoadUiData)
        }
    )
}

@Composable
internal fun SyncEodScreen(
    screenState: SyncEodScreenState,
    onBackPress: () -> Unit,
    onUiEvent: (SyncEodScreenEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                isLoading = screenState.isLoading,
                onBackPress = onBackPress,
                title = stringResource(id = R.string.sync_od)
            )
        }
    ) { paddingValues: PaddingValues ->
        if (screenState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = if (screenState.isEodSyncLoading)
                        stringResource(R.string.msg_syncing_eod_please_wait)
                    else stringResource(R.string.msg_loading),
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            SyncEodItemCard(
                                screenState.processedAmount,
                                stringResource(R.string.msg_processed),
                                MaterialTheme.colorScheme.primary,
                                icon = BanklyIcons.SyncProcessed
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            SyncEodItemCard(
                                screenState.settledAmount,
                                stringResource(R.string.msg_settled),
                                BanklySuccessColor.successColor,
                                icon = BanklyIcons.SyncSettled
                            )
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            .clip(CircleShape)
                            .clickable(
                                enabled = screenState.isUserInputEnabled,
                                onClick = {
                                    onUiEvent(SyncEodScreenEvent.OnSyncTapEod)
                                },
                                role = Role.Button,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = true,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            )
                            .padding(vertical = 6.dp, horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.action_tap_to_sync_eod),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.msg_sync_eod_to_get_updated_transaction_report),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.tertiary
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }

    BanklyCenterDialog(
        title = stringResource(R.string.title_error),
        subtitle = screenState.errorDialogMessage,
        icon = BanklyIcons.ErrorAlert,
        positiveActionText = stringResource(R.string.action_dismiss),
        positiveAction = {
            onUiEvent(SyncEodScreenEvent.OnDismissErrorDialog)
        },
        showDialog = screenState.showErrorDialog,
        onDismissDialog = {
            onUiEvent(SyncEodScreenEvent.OnDismissErrorDialog)
        }
    )

    BanklyCenterDialog(
        title = stringResource(R.string.title_eod_sync_successful),
        subtitle = screenState.successfulEodSyncMessage,
        icon = BanklyIcons.Successful,
        positiveActionText = stringResource(R.string.action_dismiss),
        positiveAction = {
            onUiEvent(SyncEodScreenEvent.OnDismissSuccessfulEodSyncDialog)
        },
        showDialog = screenState.showSuccessfulEodSyncDialog,
        onDismissDialog = {
            onUiEvent(SyncEodScreenEvent.OnDismissSuccessfulEodSyncDialog)
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun SyncEodScreenPreview() {
    BanklyTheme {
        SyncEodScreen(
            screenState = SyncEodScreenState(),
            onBackPress = {},
            onUiEvent = {}
        )
    }
}