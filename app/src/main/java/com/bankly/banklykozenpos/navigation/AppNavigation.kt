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
import com.bankly.feature.dashboard.navigation.dashBoardNavGraphRoute
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraph
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraphRoute

@Composable
fun AppNavHost(
    appState: BanklyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = authenticationNavGraphRoute,
    onBackPress: () -> Unit
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
            onBackPress = onBackPress,
        )
        dashBoardNavGraph(
            onBackPress = onBackPress
        )
        payWithCardNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            }
        )
    }
}



