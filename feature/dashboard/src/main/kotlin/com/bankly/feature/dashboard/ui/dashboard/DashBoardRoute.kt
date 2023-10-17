package com.bankly.feature.dashboard.ui.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.dashboard.R
import com.bankly.feature.dashboard.model.DashboardTab
import com.bankly.feature.dashboard.navigation.BottomNavDestination
import com.bankly.feature.dashboard.ui.component.DashBoardAppBar
import com.bankly.feature.dashboard.ui.component.DashBoardBottomNavBar

@Composable
fun DashBoardRoute(
    showTopAppBar: Boolean,
    currentBottomNavDestination: BottomNavDestination,
    onNavigateToBottomNavDestination: (BottomNavDestination) -> Unit,
    showBottomNavBar: Boolean,
    content: @Composable (PaddingValues) -> Unit,
    currentTab: DashboardTab,
    onTabChange: (DashboardTab) -> Unit,
    onBackPress: () -> Unit,
    showLoadingIndicator: Boolean,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val showActionDialog = remember { mutableStateOf(false) }

    BackHandler {
        if (currentTab == DashboardTab.POS) {
            onTabChange(DashboardTab.Home)
        } else {
            showActionDialog.value = true
        }
    }

    if (showActionDialog.value) {
        BanklyActionDialog(
            title = stringResource(R.string.title_confirm_action),
            subtitle = stringResource(R.string.msg_are_you_sure_you_want_to_exit_the_app),
            negativeActionText = stringResource(R.string.action_yes),
            negativeAction = { onBackPress() },
            positiveActionText = stringResource(R.string.action_no),
            positiveAction = { showActionDialog.value = false },
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopAppBar) {
                when (currentBottomNavDestination) {
                    BottomNavDestination.HOME -> {
                        DashBoardAppBar(
                            selectedTab = currentTab,
                            onTabChange = onTabChange,
                        )
                    }
                    BottomNavDestination.TRANSACTIONS -> {
                        BanklyTitleBar(
                            isLoading = showLoadingIndicator,
                            title = currentBottomNavDestination.title ?: "",
                            onTrailingIconClick = {

                            },
                            trailingIcon = { onClick: () -> Unit ->
                                Text(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable(
                                            onClick = onClick,
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(
                                                bounded = true,
                                                color = MaterialTheme.colorScheme.primary,
                                            ),
                                        )
                                        .padding(8.dp),
                                    text = buildAnnotatedString {
                                        withStyle(
                                            MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
                                                .toSpanStyle()
                                        ) {
                                            append(stringResource(R.string.action_export))
                                        }
                                    })
                            }
                        )
                    }
                    BottomNavDestination.SUPPORT, BottomNavDestination.MORE-> {
                        BanklyTitleBar(
                            title = currentBottomNavDestination.title ?: "",
                        )
                    }
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNavBar,
                exit = fadeOut() + slideOutHorizontally()
            ) {
                DashBoardBottomNavBar(
                    destinations = BottomNavDestination.values()
                        .filter { it.isBottomNavDestination },
                    onNavigateToBottomNavDestination = onNavigateToBottomNavDestination,
                    currentBottomNavDestination = currentBottomNavDestination,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) { padding -> content(padding) }
}

@Preview(showBackground = true)
@Composable
fun DashBoardRoutePreview() {
    BanklyTheme {
        DashBoardRoute(
            showTopAppBar = true,
            currentBottomNavDestination = BottomNavDestination.HOME,
            onNavigateToBottomNavDestination = {},
            showBottomNavBar = true,
            content = {},
            currentTab = DashboardTab.Home,
            onTabChange = {},
            onBackPress = {},
            showLoadingIndicator = false,
        )
    }
}
