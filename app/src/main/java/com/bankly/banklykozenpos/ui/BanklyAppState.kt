package com.bankly.banklykozenpos.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bankly.banklykozenpos.navigation.BottomNavDestination
import com.bankly.banklykozenpos.navigation.TopLevelDestination
import com.bankly.banklykozenpos.navigation.dashBoardNavGraph
import com.bankly.banklykozenpos.navigation.dashBoardRoute
import com.bankly.banklykozenpos.navigation.homeScreen
import com.bankly.banklykozenpos.navigation.moreScreen
import com.bankly.banklykozenpos.navigation.navigateToAuthenticationNavGraph
import com.bankly.banklykozenpos.navigation.navigateToDashBoardNavGraph
import com.bankly.banklykozenpos.navigation.navigateToHome
import com.bankly.banklykozenpos.navigation.navigateToMore
import com.bankly.banklykozenpos.navigation.navigateToSupport
import com.bankly.banklykozenpos.navigation.navigateToTransactions
import com.bankly.banklykozenpos.navigation.supportScreen
import com.bankly.banklykozenpos.navigation.transactionsScreen
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.feature.authentication.navigation.authenticationNavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberBanklyAppState(
    context: Context,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    topLevelNavHostController: NavHostController = rememberNavController(),
    dashBoardNavHostController: NavHostController = rememberNavController(),
): BanklyAppState {
    return remember(
        topLevelNavHostController,
        coroutineScope,
        networkMonitor
    ) {
        BanklyAppState(
            topLevelNavHostController,
            dashBoardNavHostController,
            coroutineScope,
            networkMonitor
        )
    }
}

@Stable
class BanklyAppState(
    val topLevelNavHostController: NavHostController,
    val dashBoardNavHostController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (topLevelNavHostController.currentBackStackEntryAsState().value?.destination?.route?.substringBefore(
            "/"
        )) {
            authenticationNavGraph -> TopLevelDestination.AUTHENTICATION
            dashBoardNavGraph -> TopLevelDestination.DASHBOARD
            else -> null
        }

    val currentBottomNavDestination: BottomNavDestination?
        @Composable get() {
            val currentDashBoardDestination =
                dashBoardNavHostController.currentBackStackEntryAsState().value?.destination?.route
            Log.d(
                "app state debug",
                "current bottom nav destination route: $currentDashBoardDestination"
            )
            return when (currentDashBoardDestination) {
                homeScreen -> BottomNavDestination.HOME
                transactionsScreen -> BottomNavDestination.TRANSACTIONS
                supportScreen -> BottomNavDestination.SUPPORT
                moreScreen -> BottomNavDestination.MORE
                else -> null
            }
        }

    val shouldShowBottomNavBar: Boolean
        @Composable get() {
            Log.d(
                "app state debug",
                "current top level destination route: ${topLevelNavHostController.currentBackStackEntryAsState().value?.destination?.route}"
            )
            return currentTopLevelDestination == TopLevelDestination.DASHBOARD
        }

    val shouldShowTopAppBar: Boolean
        @Composable get() = currentBottomNavDestination == BottomNavDestination.HOME

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    fun navigateTo(destination: BottomNavDestination) {
        val navOption = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(dashBoardNavHostController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }

        when (destination) {
            BottomNavDestination.HOME -> dashBoardNavHostController.navigateToHome(navOption)
            BottomNavDestination.TRANSACTIONS -> dashBoardNavHostController.navigateToTransactions(
                navOption
            )

            BottomNavDestination.SUPPORT -> dashBoardNavHostController.navigateToSupport(navOption)
            BottomNavDestination.MORE -> dashBoardNavHostController.navigateToMore(navOption)
        }
    }

    fun navigateTo(destination: TopLevelDestination) {
        val navOption = navOptions {
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }

        when (destination) {
            TopLevelDestination.AUTHENTICATION -> topLevelNavHostController
                .navigateToAuthenticationNavGraph(navOption)

            TopLevelDestination.DASHBOARD -> topLevelNavHostController
                .navigateToDashBoardNavGraph(navOption)
        }
    }

}