package com.bankly.banklykozenpos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.bankly.banklykozenpos.ui.BanklyAppState
import com.bankly.feature.authentication.navigation.authenticationNavGraph
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.dashboard.model.QuickAction
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraph

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
            onBackPress = onBackPress,
            onQuickActionCardClick = { quickAction: QuickAction ->
                when (quickAction) {
                    QuickAction.PayWithCard -> appState.navigateTo(TopLevelDestination.PAY_WITH_CARD)
                    QuickAction.PayWithTransfer -> appState.navigateTo(TopLevelDestination.PAY_WITH_TRANSFER)
                    QuickAction.PayWithCash -> appState.navigateTo(TopLevelDestination.PAY_WITH_CASH)
                    QuickAction.SendMoney -> appState.navigateTo(TopLevelDestination.SEND_MONEY)
                }
            }
        )
        payWithCardNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            }
        )
    }
}



