package com.bankly.banklykozenpos.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bankly.banklykozenpos.navigation.navigateToAuthenticationNavGraph
import com.bankly.banklykozenpos.navigation.navigateToDashBoardNavGraph
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.banklykozenpos.navigation.TopLevelDestination
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
            launchSingleTop = true
            restoreState = true
//            navHostController.currentDestination?.route?.let {
//                popUpTo(it) {
//                    inclusive = true
//                }
//            }
        }
        Log.d("debug", "current destination: ${navHostController.currentDestination?.route}")
        when (destination) {
            TopLevelDestination.AUTHENTICATION -> navHostController
                .navigateToAuthenticationNavGraph(navOption)

            TopLevelDestination.DASHBOARD -> navHostController
                .navigateToDashBoardNavGraph(navOption)
        }
    }
}