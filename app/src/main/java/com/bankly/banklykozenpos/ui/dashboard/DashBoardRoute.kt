package com.bankly.banklykozenpos.ui.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import com.bankly.banklykozenpos.model.DashboardTab
import com.bankly.banklykozenpos.navigation.BottomNavDestination
import com.bankly.core.designsystem.component.BanklyTitleBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardRoute(
    showTopAppBar: Boolean,
    currentBottomNavDestination: BottomNavDestination?,
    onNavigateToBottomNavDestination: (BottomNavDestination) -> Unit,
    showBottomNavBar: Boolean,
    content: @Composable (PaddingValues) -> Unit,
    currentTab: DashboardTab,
    onTabChange: (DashboardTab) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

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
                    destinations = BottomNavDestination.values().filter { it.isBottomNavDestination },
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