package com.bankly.banklykozenpos.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bankly.banklykozenpos.navigation.TopLevelDestination
import com.bankly.banklykozenpos.navigation.TopLevelDestination.AUTHENTICATION
import com.bankly.banklykozenpos.navigation.TopLevelDestination.DASHBOARD
import com.bankly.banklykozenpos.navigation.TopLevelDestination.PAYWITHCARD
import com.bankly.banklykozenpos.navigation.navigateToAuthenticationNavGraph
import com.bankly.banklykozenpos.navigation.navigateToDashBoardNavGraph
import com.bankly.banklykozenpos.navigation.navigateToPayWithCardNavGraph
import com.bankly.core.data.util.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberBanklyAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberNavController(),
): BanklyAppState {
    return remember(
        navHostController,
        coroutineScope,
        networkMonitor
    ) {
        BanklyAppState(
            navHostController,
            coroutineScope,
            networkMonitor
        )
    }
}

@Stable
data class BanklyAppState(
    val navHostController: NavHostController,
    private val coroutineScope: CoroutineScope,
    val networkMonitor: NetworkMonitor,
) {
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    fun navigateTo(destination: TopLevelDestination) {
        val navOption = navOptions {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (destination) {
            AUTHENTICATION -> navHostController.navigateToAuthenticationNavGraph(navOption)
            DASHBOARD -> navHostController.navigateToDashBoardNavGraph(navOption)
            PAYWITHCARD -> navHostController.navigateToPayWithCardNavGraph(navOption)
        }
    }
}