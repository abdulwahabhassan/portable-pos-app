package com.bankly.banklykozenpos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.bankly.banklykozenpos.ui.BanklyAppState
import com.bankly.feature.authentication.navigation.authenticationNavGraph
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph

@Composable
fun AppNavHost(
    appState: BanklyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = authenticationNavGraphRoute,
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
            onBackPress = onPopAuthenticationNavGraph,
        )
        dashBoardNavGraph(
            onBackPress = onPopDashBoardNavGraph
        )
    }
}

internal fun NavHostController.navigateToAuthenticationNavGraph(
    navOptions: NavOptions? = null
) {
    this.navigate(authenticationNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToDashBoardNavGraph(
    navOptions: NavOptions? = null
) {
    this.navigate(dashBoardNavGraph, navOptions)
}




