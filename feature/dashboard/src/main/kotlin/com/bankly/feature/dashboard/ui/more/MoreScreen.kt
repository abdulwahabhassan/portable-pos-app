package com.bankly.feature.dashboard.ui.more

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.Feature
import com.bankly.feature.dashboard.R
import com.bankly.feature.dashboard.ui.component.FeatureCard
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun MoreRoute(
    viewModel: MoreScreenViewModel = hiltViewModel(),
    onFeatureCardClick: (Feature) -> Unit,
    onBackPress: () -> Unit,
    onLogOutClick: () -> Unit,
) {
    val screenState = viewModel.uiState.collectAsStateWithLifecycle().value
    var showComingSoonDialog by remember { mutableStateOf(false) }

    BanklyCenterDialog(
        title = stringResource(com.bankly.core.common.R.string.title_coming_soon),
        subtitle = stringResource(id = com.bankly.core.common.R.string.msg_this_feature_is_not_yet_available),
        icon = BanklyIcons.ComingSoon,
        showDialog = showComingSoonDialog,
        onDismissDialog = { showComingSoonDialog = false },
        extraContent = {
            Column {
                BanklyFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                    text = "Okay",
                    onClick = { showComingSoonDialog = false },
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    )

    MoreScreen(
        screenState = screenState,
        onBackPress = onBackPress,
        onUIEvent = { event: MoreScreenEvent ->
            viewModel.sendEvent(event)
        },
        onLogOutClick = onLogOutClick,
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.oneShotState.onEach { oneshotState: MoreScreenOneShotState ->
                when (oneshotState) {
                    is MoreScreenOneShotState.GoToFeature -> {
                        when (oneshotState.feature) {
                            is Feature.PayWithUssd, is Feature.Float -> {
                                showComingSoonDialog = true
                            }

                            else -> onFeatureCardClick(oneshotState.feature)
                        }

                    }
                }
            }.launchIn(this)
        },
    )

    BanklyCenterDialog(
        title = stringResource(id = R.string.title_access_denied),
        subtitle = stringResource(id = R.string.msg_enable_feature_in_settings),
        icon = BanklyIcons.AccessDenied,
        showDialog = screenState.showFeatureAccessDeniedDialog,
        onDismissDialog = {
            viewModel.sendEvent(MoreScreenEvent.OnDismissFeatureAccessDeniedDialog)
        },
        showCloseIcon = true,
        extraContent = {
            BanklyFilledButton(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.action_go_to_settings),
                onClick = {
                    viewModel.sendEvent(MoreScreenEvent.OnDismissFeatureAccessDeniedDialog)
                    viewModel.sendEvent(MoreScreenEvent.OnFeatureCardClick(Feature.Settings()))
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
            )
        },
    )
}

@Composable
internal fun MoreScreen(
    screenState: MoreScreenState,
    onBackPress: () -> Unit,
    onLogOutClick: () -> Unit,
    onUIEvent: (MoreScreenEvent) -> Unit,
) {
    val context = LocalContext.current
    BackHandler {
        onBackPress()
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(Feature.values().filter { it.isQuickAction.not() }.chunked(2)) {
            Row {
                if (it.firstOrNull() != null) {
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                        FeatureCard(
                            feature = it.first(),
                            onClick = {
                                onUIEvent(MoreScreenEvent.OnFeatureCardClick(it.first()))
                            },
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.weight(1f),
                    )
                }

                if (it.size == 2) {
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                        FeatureCard(
                            feature = it.last(),
                            onClick = {
                                onUIEvent(MoreScreenEvent.OnFeatureCardClick(it.last()))
                            },
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            BanklyFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                text = stringResource(R.string.action_log_out),
                onClick = {
                    onUIEvent(MoreScreenEvent.OnLogOutClick)
                },
                backgroundColor = MaterialTheme.colorScheme.errorContainer,
                textColor = MaterialTheme.colorScheme.error,
                startIcon = {
                    Icon(
                        painter = painterResource(id = BanklyIcons.Logout),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                },
            )
        }
    }
    BanklyCenterDialog(
        title = context.getString(R.string.action_log_out),
        showDialog = screenState.showLogoutWarningDialog,
        subtitle = context.getString(R.string.msg_are_you_sure_you_want_to_logout),
        icon = BanklyIcons.ErrorAlert,
        onDismissDialog = {
            onUIEvent(MoreScreenEvent.OnDismissLogOutWarningDialog)
        },
        positiveActionText = stringResource(R.string.action_cancel),
        negativeAction = onLogOutClick,
        negativeActionText = stringResource(R.string.action_yes_logout),
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun MoreScreenPreview() {
    BanklyTheme {
        MoreScreen(
            screenState = MoreScreenState(showLogoutWarningDialog = false),
            onBackPress = {},
            onUIEvent = {},
            onLogOutClick = {},
        )
    }
}
