package com.bankly.banklykozenpos.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bankly.banklykozenpos.navigation.BottomNavDestination
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.feature.authentication.navigation.loginRoute
import com.bankly.feature.authentication.navigation.navigateToAuthenticationNavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberBanklyAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): BanklyAppState {
    return remember(
        navController,
        coroutineScope,
        networkMonitor
    ) {
        BanklyAppState(
            navController,
            coroutineScope,
            networkMonitor
        )
    }
}

@Stable
class BanklyAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: BottomNavDestination?
        @Composable get() = when (currentDestination?.route) {
            loginRoute -> BottomNavDestination.HOME
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = true

    val shouldShowTopAppBar: Boolean
        @Composable get() = currentTopLevelDestination == BottomNavDestination.HOME

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val bottomNavDestinations: List<BottomNavDestination> = BottomNavDestination.values().asList()

    fun navigateToBottomNavDestination(bottomNavDestination: BottomNavDestination) {
        val navOption = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }

        when (bottomNavDestination) {
            BottomNavDestination.HOME -> navController.navigateToAuthenticationNavGraph(navOption)
            BottomNavDestination.TRANSACTIONS -> {}
            BottomNavDestination.SUPPORT -> {}
            BottomNavDestination.MORE -> {}
        }
    }

}