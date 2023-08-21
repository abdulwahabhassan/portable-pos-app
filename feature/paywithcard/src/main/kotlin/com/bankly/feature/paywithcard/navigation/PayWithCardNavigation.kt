package com.bankly.feature.paywithcard.navigation

import ProcessPayment
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.TransactionType
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.helper.ConfigParameters
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools

fun NavGraphBuilder.payWithCardNavGraph(
    onBackPress: () -> Unit,
    appNavController: NavHostController,
    activity: Activity
) {
    Tools.initializeEmv(activity)
    ConfigParameters.downloadTmsParams(activity)
    Tools.transactionType = com.bankly.kozonpaymentlibrarymodule.posservices.TransactionType.CASHOUT

    navigation(
        route = "$payWithCardNavGraphRoute/{$amountArg}",
        startDestination = payWithCardRoute,
        arguments = listOf(
            navArgument(amountArg) { type = NavType.StringType },
        )
    ) {
        composable(payWithCardRoute) { backStackEntry: NavBackStackEntry ->
            val parentEntry = remember(backStackEntry) {
                appNavController.getBackStackEntry(payWithCardRoute)
            }
            val amount = parentEntry.arguments?.getString(
                amountArg
            )?.toDouble() ?: 0.00
            Log.d("debug amount", "$amount")
            Tools.transactionAmount = amount.toString()
            var payWithCardState by rememberPayWithCardState()
            PayWithCardNavHost(
                navHostController = payWithCardState.navHostController,
                onBackPress = onBackPress,
                activity = activity
            )
        }
    }
}

@Composable
private fun PayWithCardNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    activity: Activity
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = selectAccountTypeRoute,
    ) {
        selectAccountTypeRoute(
            onAccountSelected = { accountType: AccountType ->
                Tools.accountType = accountType.name
                ProcessPayment(activity) { transactionResponse, dialogDismiss ->
                    dialogDismiss
                    Log.d("debug transaction data", "$transactionResponse")
                }
//                navHostController.navigateToInsertCardRoute()
            },
            onBackPress = onBackPress,
        )
        insertCardRoute(
            onCardInserted = {
                navHostController.navigateToEnterPinRoute()
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        enterCardPinRoute(
            onContinueClick = {
                navHostController.navigateToProcessTransactionRoute(
                    TransactionData.mockTransactionData()
                        .copy(transactionType = TransactionType.CARD_WITHDRAWAL)
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        processTransactionRoute(
            onSuccessfulTransaction = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionSuccessRoute(transactionReceipt = transactionReceipt)
            },
            onFailedTransaction = { message: String ->
                navHostController.navigateToTransactionFailedRoute(message = message)
            }
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionDetailsRoute(transactionReceipt = transactionReceipt)
            },
            onGoHomeClick = onBackPress
        )
        transactionFailedRoute(
            onGoHomeClick = onBackPress
        )
        transactionDetailsRoute(
            onShareClick = { },
            onSmsClick = { },
            onLogComplaintClick = { },
            onGoToHomeClick = onBackPress
        )
    }
}