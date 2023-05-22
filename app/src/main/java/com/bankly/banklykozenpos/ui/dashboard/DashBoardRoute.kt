package com.bankly.banklykozenpos.ui.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import com.bankly.banklykozenpos.model.DashboardTab
import com.bankly.banklykozenpos.navigation.BottomNavDestination
import com.bankly.core.designsystem.component.ActionDialog
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.feature.authentication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardRoute(
    showTopAppBar: Boolean,
    currentBottomNavDestination: BottomNavDestination?,
    onNavigateToBottomNavDestination: (BottomNavDestination) -> Unit,
    showBottomNavBar: Boolean,
    content: @Composable (PaddingValues) -> Unit,
    currentTab: DashboardTab,
    onTabChange: (DashboardTab) -> Unit,
    onBackClick: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val showActionDialog = remember { mutableStateOf(false) }

    BackHandler {
        showActionDialog.value = true
    }

    if (showActionDialog.value) {
        ActionDialog(
            title = "Confirm action",
            subtitle = "Are you sure you want to exit the app?",
            positiveActionText = stringResource(R.string.action_yes),
            positiveAction = { onBackClick() },
            negativeActionText = stringResource(R.string.action_no),
            negativeAction = { showActionDialog.value = false })
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopAppBar) {
                if (currentBottomNavDestination?.route == BottomNavDestination.HOME.route) {
                    DashBoardAppBar(
                        selectedTab = currentTab,
                        onTabChange = onTabChange
                    )
                } else {
                    BanklyTitleBar(
                        onBackClick = {},
                        title = "",
                        subTitle = buildAnnotatedString { append("") }
                    )
                }
            }
        },
        bottomBar = {
            if (showBottomNavBar) {
                DashBoardBottomNavBar(
                    destinations = BottomNavDestination.values()
                        .filter { it.isBottomNavDestination },
                    onNavigateToBottomNavDestination = onNavigateToBottomNavDestination,
                    currentBottomNavDestination = currentBottomNavDestination
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding -> content(padding) }

}