package com.bankly.feature.dashboard.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.feature.dashboard.DashBoardRoute
import com.bankly.feature.dashboard.model.DashboardTab
import com.bankly.feature.dashboard.ui.home.HomeScreen
import com.bankly.feature.dashboard.ui.more.MoreScreen
import com.bankly.feature.dashboard.ui.pos.PosScreen
import com.bankly.feature.dashboard.ui.support.SupportScreen
import com.bankly.feature.dashboard.ui.transactions.TransactionsScreen

const val dashBoardNavGraph = "dashboard_graph"
const val dashBoardRoute = dashBoardNavGraph.plus("/dashboard_route")
const val transactionsScreen = dashBoardRoute.plus("/transactions_screen")
const val homeScreen = dashBoardRoute.plus("/home_screen")
const val supportScreen = dashBoardRoute.plus("/support_screen")
const val moreScreen = dashBoardRoute.plus("/more_screen")

fun NavGraphBuilder.dashBoardNavGraph(
    onPopDashboardScreen: () -> Unit
) {
    navigation(
        route = dashBoardNavGraph,
        startDestination = dashBoardRoute,
    ) {
        composable(dashBoardRoute) {
            var dashBoardState by rememberDashBoardState()
            //Log.d("debug", "current dashboard destination: ${dashBoardState.navHostController.currentDestination?.route}")
            DashBoardRoute(
                showTopAppBar = dashBoardState.shouldShowTopAppBar,
                currentBottomNavDestination = dashBoardState.currentBottomNavDestination,
                onNavigateToBottomNavDestination = { destination ->
                    dashBoardState.navigateTo(destination)
                },
                showBottomNavBar = dashBoardState.shouldShowBottomNavBar,
                content = { padding ->
                    DashBoardBottomNavHost(
                        currentHomeTab = dashBoardState.currentTab,
                        modifier = Modifier.padding(padding),
                        navHostController = dashBoardState.navHostController
                    )
                },
                currentTab = dashBoardState.currentTab,
                onTabChange = { tab: DashboardTab ->
                    dashBoardState = dashBoardState.copy(currentTab = tab)
                },
                onBackClick = onPopDashboardScreen
            )
        }
    }

}

@Composable
fun DashBoardBottomNavHost(
    currentHomeTab: DashboardTab,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = homeScreen,
    ) {
        composable(route = homeScreen) {
            when (currentHomeTab) {
                DashboardTab.POS -> {
                    PosScreen {}
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

