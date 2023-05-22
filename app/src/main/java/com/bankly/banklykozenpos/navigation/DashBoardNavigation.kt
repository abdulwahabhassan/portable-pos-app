package com.bankly.banklykozenpos.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.banklykozenpos.model.DashboardTab
import com.bankly.banklykozenpos.ui.BanklyAppState
import com.bankly.banklykozenpos.ui.dashboard.DashBoardRoute
import com.bankly.banklykozenpos.ui.dashboard.HomeScreen
import com.bankly.banklykozenpos.ui.dashboard.MoreScreen
import com.bankly.banklykozenpos.ui.dashboard.PosScreen
import com.bankly.banklykozenpos.ui.dashboard.SupportScreen
import com.bankly.banklykozenpos.ui.dashboard.TransactionsScreen

const val dashBoardNavGraph = "dashboard_graph"
const val dashBoardRoute = dashBoardNavGraph.plus("/dashboard_route")
const val transactionsScreen = dashBoardRoute.plus("/transactions_screen")
const val homeScreen = dashBoardRoute.plus("/home_screen")
const val supportScreen = dashBoardRoute.plus("/support_screen")
const val moreScreen = dashBoardRoute.plus("/more_screen")

fun NavGraphBuilder.dashBoardNavGraph(
    appState: BanklyAppState,
    onPopDashboardScreen: () -> Unit
) {
    navigation(
        route = dashBoardNavGraph,
        startDestination = dashBoardRoute,
    ) {
        composable(dashBoardRoute) {
            var currentTab by remember { mutableStateOf(DashboardTab.Home) }
            DashBoardRoute(
                showTopAppBar = appState.shouldShowTopAppBar,
                currentBottomNavDestination = appState.currentBottomNavDestination,
                onNavigateToBottomNavDestination = { destination: BottomNavDestination ->
                    appState.navigateTo(destination)
                },
                showBottomNavBar = appState.shouldShowBottomNavBar && currentTab == DashboardTab.Home,
                content = { padding ->
                    DashBoardNavHost(
                        currentHomeTab = currentTab,
                        modifier = Modifier.padding(padding),
                        navHostController = appState.dashBoardNavHostController,
                        onNavigateToTopLevelDestination = { destination: TopLevelDestination ->
                            appState.navigateTo(destination)
                        },
                    )
                },
                currentTab = currentTab,
                onTabChange = { tab: DashboardTab -> currentTab = tab },
                onBackClick = onPopDashboardScreen
            )
        }
    }

}

@Composable
fun DashBoardNavHost(
    currentHomeTab: DashboardTab,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = homeScreen,
    ) {
        composable(route = homeScreen) {
            when (currentHomeTab) {
                DashboardTab.POS -> {
                    PosScreen()
                }

                DashboardTab.Home -> {
                    HomeScreen()
                }
            }
        }
        composable(route = transactionsScreen) {
            TransactionsScreen()
        }

        composable(route = supportScreen) {
            SupportScreen()
        }

        composable(route = moreScreen) {
            MoreScreen()
        }
    }
}

internal fun NavHostController.navigateToHome(
    navOptions: NavOptions? = null
) {
    this.navigate(homeScreen, navOptions)
}

internal fun NavHostController.navigateToTransactions(
    navOptions: NavOptions? = null
) {
    this.navigate(transactionsScreen, navOptions)
}

internal fun NavHostController.navigateToSupport(
    navOptions: NavOptions? = null
) {
    this.navigate(supportScreen, navOptions)
}

internal fun NavHostController.navigateToMore(
    navOptions: NavOptions? = null
) {
    this.navigate(moreScreen, navOptions)
}

