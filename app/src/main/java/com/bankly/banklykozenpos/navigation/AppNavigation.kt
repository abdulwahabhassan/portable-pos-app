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
import com.bankly.feature.dashboard.model.Feature
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph
import com.bankly.feature.paybills.navigation.billPaymentNavGraph
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraph
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraphRoute
import com.bankly.feature.paywithtransfer.navigation.payWithTransferNavGraph
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
            onFeatureClick = { feature: Feature ->
                appState.navigateTo(
                    when (feature) {
                        Feature.PayWithCard -> TopLevelDestination.PAY_WITH_CARD
                        Feature.PayWithTransfer -> TopLevelDestination.PAY_WITH_TRANSFER
                        Feature.CardTransfer -> TopLevelDestination.CARD_TRANSFER
                        Feature.SendMoney -> TopLevelDestination.SEND_MONEY
                        Feature.PayBills -> TopLevelDestination.PAY_BILLS
                        Feature.CheckBalance -> TODO()
                        Feature.PayWithUssd -> TODO()
                        Feature.Float -> TODO()
                        Feature.EndOfDay -> TODO()
                        Feature.NetworkChecker -> TODO()
                        Feature.Settings -> TODO()
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
            onForgotPinClick = {},
        )
        payWithTransferNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
        )
        billPaymentNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onForgotPinClick = {}
        )
    }
}
