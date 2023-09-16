package com.bankly.banklykozenpos.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.bankly.banklykozenpos.ui.BanklyAppState
import com.bankly.feature.authentication.navigation.authenticationNavGraph
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.cardtransfer.navigation.cardTransferNavGraph
import com.bankly.feature.dashboard.model.QuickAction
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraph
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraphRoute
import com.bankly.feature.sendmoney.navigation.sendMoneyNavGraph

@Composable
fun AppNavHost(
    appState: BanklyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = authenticationNavGraphRoute,
    onBackPress: () -> Unit,
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
                appState.navigateTo(
                    when (quickAction) {
                        QuickAction.PayWithCard -> {
                            TopLevelDestination.PAY_WITH_CARD
                        }
                        QuickAction.PayWithTransfer -> TopLevelDestination.PAY_WITH_TRANSFER
                        QuickAction.CardTransfer -> TopLevelDestination.CARD_TRANSFER
                        QuickAction.SendMoney -> TopLevelDestination.SEND_MONEY
                    },
                )
            },
            onContinueToPayWithCardClick = { amount: Double ->
                val encodedAmount = Uri.encode(amount.toString())
                val navOption = navOptions {
                    launchSingleTop = true
                    restoreState = true
                }
                appState.navHostController.navigate(
                    route = "$payWithCardNavGraphRoute/$encodedAmount",
                    navOptions = navOption,
                )
            },
        )
        payWithCardNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            appNavController = appState.navHostController,
        )
        cardTransferNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
        )
        sendMoneyNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onForgotPinClick = {
            },
        )
    }
}
