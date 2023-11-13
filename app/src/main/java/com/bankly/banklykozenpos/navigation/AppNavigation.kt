package com.bankly.banklykozenpos.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.bankly.banklykozenpos.ui.BanklyAppState
import com.bankly.feature.authentication.navigation.authenticationNavGraph
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.cardtransfer.navigation.cardTransferNavGraph
import com.bankly.feature.contactus.navigation.contactUsNavGraph
import com.bankly.core.entity.Feature
import com.bankly.feature.authentication.navigation.isValidatePassCodeArg
import com.bankly.feature.checkcardbalance.navigation.checkCardBalanceNavGraph
import com.bankly.feature.dashboard.model.SupportOption
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph
import com.bankly.feature.eod.navigation.eodNavGraph
import com.bankly.feature.logcomplaints.navigation.logComplaintNavGraph
import com.bankly.feature.networkchecker.navigation.networkCheckerNavGraph
import com.bankly.feature.paybills.navigation.billPaymentNavGraph
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraph
import com.bankly.feature.paywithtransfer.navigation.payWithTransferNavGraph
import com.bankly.feature.sendmoney.navigation.sendMoneyNavGraph
import com.bankly.feature.settings.navigation.settingsNavGraph
import com.bankly.feature.transactiondetails.navigation.transactionDetailsNavGraph


@Composable
fun AppNavHost(
    appState: BanklyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = "$authenticationNavGraphRoute/{$isValidatePassCodeArg}",
    onExitApp: () -> Unit,
    onLogOutClick: () -> Unit,
    activity: Activity
) {
    NavHost(
        modifier = modifier,
        navController = appState.navHostController,
        startDestination = startDestination,
    ) {
        authenticationNavGraph(
            appNavController = appState.navHostController,
            onLoginSuccess = {
                appState.navigateTo(AppTopLevelDestination.DASHBOARD)
            },
            onBackPress = onExitApp,
            onGoToSettingsRoute = {
                appState.navHostController.navigateToSettingsNavGraph(
                    navOptions = navOptions {
                        popUpTo("$authenticationNavGraphRoute/{$isValidatePassCodeArg}") {
                            inclusive = true
                        }
                    }
                )
            },
            onPopBackStack = {
                appState.navHostController.popBackStack()
            },
            onContactSupportPress = {
                appState.navHostController.navigateToContactUsNavGraph()
            }
        )
        dashBoardNavGraph(
            onExitApp = onExitApp,
            onFeatureClick = { feature: Feature ->
                appState.navigateTo(
                    when (feature) {
                        is Feature.PayWithCard -> AppTopLevelDestination.PAY_WITH_CARD
                        is Feature.PayWithTransfer -> AppTopLevelDestination.PAY_WITH_TRANSFER
                        is Feature.CardTransfer -> AppTopLevelDestination.CARD_TRANSFER
                        is Feature.SendMoney -> AppTopLevelDestination.SEND_MONEY
                        is Feature.PayBills -> AppTopLevelDestination.PAY_BILLS
                        is Feature.CheckBalance -> AppTopLevelDestination.CHECK_BALANCE
                        is Feature.PayWithUssd -> AppTopLevelDestination.PAY_WITH_USSD
                        is Feature.Float -> AppTopLevelDestination.FLOAT
                        is Feature.EndOfDay -> AppTopLevelDestination.EOD
                        is Feature.NetworkChecker -> AppTopLevelDestination.NETWORK_CHECKER
                        is Feature.Settings -> AppTopLevelDestination.SETTINGS
                    },
                )
            },
            onContinueToPayWithCardClick = { amount: Double ->
                appState.navHostController.navigateToPayWithCardNavGraph(
                    amount = amount,
                    navOptions = navOptions {
                        launchSingleTop = true
                        restoreState = true
                    })
            },
            onGoToTransactionDetailsScreen = { transactionReceipt ->
                appState.navHostController.navigateToTransactionDetailsNavGraph(transactionReceipt)
            },
            onSupportOptionClick = { supportOption ->
                when (supportOption) {
                    SupportOption.FAQ -> {}
                    SupportOption.CONTACT_US -> {
                        appState.navHostController.navigateToContactUsNavGraph()
                    }

                    SupportOption.LOG_COMPLAINT -> {
                        appState.navHostController.navigateToLogComplaintNavGraph()
                    }
                }
            },
            onLogOutClick = onLogOutClick,
            activity = activity
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
        payWithTransferNavGraph(
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
        networkCheckerNavGraph(
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
        transactionDetailsNavGraph(
            appNavController = appState.navHostController,
            onBackPress = {
                appState.navHostController.popBackStack()
            }
        )
        eodNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            }
        )
        contactUsNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            }
        )

        logComplaintNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            }
        )
        settingsNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            }
        )
        checkCardBalanceNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
        )
    }
}


