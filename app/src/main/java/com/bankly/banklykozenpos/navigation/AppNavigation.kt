package com.bankly.banklykozenpos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.bankly.banklykozenpos.ui.BanklyAppState
import com.bankly.feature.authentication.navigation.authenticationNavGraph
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph

@Composable
fun AppNavHost(
    appState: BanklyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = authenticationNavGraph,
    onPopAuthenticationNavGraph: () -> Unit,
    onPopDashBoardNavGraph: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navHostController,
        startDestination = startDestination,
    ) {
        authenticationNavGraph(
            onLoginSuccess = {
                appState.navigateTo(TopLevelDestination.DASHBOARD)
            },
            onPopLoginScreen = onPopAuthenticationNavGraph,
            onBackToLoginClick = {
                appState.navigateTo(TopLevelDestination.AUTHENTICATION)
            }
        )
        dashBoardNavGraph(
            onPopDashboardScreen = onPopDashBoardNavGraph
        )
    }
}

internal fun NavHostController.navigateToAuthenticationNavGraph(
    navOptions: NavOptions? = null
) {
    this.navigate(authenticationNavGraph, navOptions)
}

internal fun NavHostController.navigateToDashBoardNavGraph(
    navOptions: NavOptions? = null
) {
    this.navigate(dashBoardNavGraph, navOptions)
}




