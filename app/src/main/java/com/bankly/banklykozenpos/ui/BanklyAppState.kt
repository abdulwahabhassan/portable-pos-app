package com.bankly.banklykozenpos.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.AUTHENTICATION
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.CARD_TRANSFER
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.CHECK_BALANCE
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.DASHBOARD
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.EOD
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.FLOAT
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.NETWORK_CHECKER
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.PAY_BILLS
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.PAY_WITH_CARD
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.PAY_WITH_TRANSFER
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.PAY_WITH_USSD
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.SEND_MONEY
import com.bankly.banklykozenpos.navigation.AppTopLevelDestination.SETTINGS
import com.bankly.banklykozenpos.navigation.navigateToAuthenticationNavGraph
import com.bankly.banklykozenpos.navigation.navigateToCardTransferNavGraph
import com.bankly.banklykozenpos.navigation.navigateToCheckCardBalancedNavGraph
import com.bankly.banklykozenpos.navigation.navigateToDashBoardNavGraph
import com.bankly.banklykozenpos.navigation.navigateToEodNavGraph
import com.bankly.banklykozenpos.navigation.navigateToNetworkCheckerNavGraph
import com.bankly.banklykozenpos.navigation.navigateToPayBillsNavGraph
import com.bankly.banklykozenpos.navigation.navigateToPayWithCardNavGraph
import com.bankly.banklykozenpos.navigation.navigateToPayWithTransferNavGraph
import com.bankly.banklykozenpos.navigation.navigateToSendMoneyNavGraph
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
        networkMonitor,
    ) {
        BanklyAppState(
            navHostController,
            coroutineScope,
            networkMonitor,
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

    fun navigateTo(destination: AppTopLevelDestination) {
        val navOption = navOptions {
            if (destination == DASHBOARD) {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
        when (destination) {
            AUTHENTICATION -> navHostController.navigateToAuthenticationNavGraph(navOption)
            DASHBOARD -> navHostController.navigateToDashBoardNavGraph(navOption)
            PAY_WITH_CARD -> navHostController.navigateToPayWithCardNavGraph(navOption)
            PAY_WITH_TRANSFER -> navHostController.navigateToPayWithTransferNavGraph(navOption)
            CARD_TRANSFER -> navHostController.navigateToCardTransferNavGraph(navOption)
            SEND_MONEY -> navHostController.navigateToSendMoneyNavGraph(navOption)
            PAY_BILLS -> navHostController.navigateToPayBillsNavGraph(navOption)
            EOD -> navHostController.navigateToEodNavGraph(navOption)
            NETWORK_CHECKER -> navHostController.navigateToNetworkCheckerNavGraph(navOption)
            SETTINGS -> navHostController.navigateToAuthenticationNavGraph(isValidatePassCode = true)
            FLOAT -> {}
            PAY_WITH_USSD -> {}
            CHECK_BALANCE -> navHostController.navigateToCheckCardBalancedNavGraph(navOption)
        }
    }
}
