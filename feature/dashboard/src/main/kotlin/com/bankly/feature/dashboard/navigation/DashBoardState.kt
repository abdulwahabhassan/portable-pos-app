package com.bankly.feature.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bankly.feature.dashboard.model.DashboardTab

@Composable
fun rememberDashBoardState(
    currentTab: DashboardTab = DashboardTab.Home,
    navHostController: NavHostController = rememberNavController(),
    showLoadingIndicator: Boolean = false,
): MutableState<DashBoardState> {
    return remember(
        currentTab,
        navHostController,
    ) {
        mutableStateOf(
            DashBoardState(
                currentTab = currentTab,
                navHostController = navHostController,
                showLoadingIndicator = showLoadingIndicator,
            ),
        )
    }
}

@Stable
data class DashBoardState(
    val currentTab: DashboardTab,
    val navHostController: NavHostController,
    val showLoadingIndicator: Boolean,
) {
    val shouldShowBottomNavBar: Boolean
        @Composable get() = currentTab == DashboardTab.Home

    val shouldShowTopAppBar: Boolean
        @Composable get() = true

    val currentBottomNavDestination: BottomNavDestination
        @Composable get() {
            return when (navHostController.currentBackStackEntryAsState().value?.destination?.route) {
                homeRoute -> BottomNavDestination.HOME
                transactionsRoute -> BottomNavDestination.TRANSACTIONS
                supportRoute -> BottomNavDestination.SUPPORT
                moreRoute -> BottomNavDestination.MORE
                else -> BottomNavDestination.HOME
            }
        }

    fun navigateTo(destination: BottomNavDestination) {
        val navOption = navOptions {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (destination) {
            BottomNavDestination.HOME -> navHostController.navigateToHomeRoute(navOption)
            BottomNavDestination.TRANSACTIONS -> navHostController.navigateToTransactionsRoute(navOption)
            BottomNavDestination.SUPPORT -> navHostController.navigateToSupportRoute(navOption)
            BottomNavDestination.MORE -> navHostController.navigateToMoreRoute(navOption)
        }
    }
}
