package com.bankly.feature.paywithcard.navigation

import ProcessPayment
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.bankly.feature.paywithcard.util.toTransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools

fun NavGraphBuilder.payWithCardNavGraph(
    onBackPress: () -> Unit,
    appNavController: NavHostController,
) {
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
            Tools.TransactionAmount = amount
            val payWithCardState by rememberPayWithCardState()
            PayWithCardNavHost(
                navHostController = payWithCardState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun PayWithCardNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    val context = LocalContext.current

    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = selectAccountTypeRoute,
    ) {
        selectAccountTypeRoute(
            onAccountSelected = { accountType: AccountType ->
                val acctType = when(accountType) {
                    AccountType.SAVINGS -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.SAVINGS
                            AccountType.DEFAULT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.DEFAULT
                            AccountType.CREDIT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CREDIT
                            AccountType.CURRENT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CURRENT
                }
                Tools.SetAccountType(acctType)
                ProcessPayment(context) { transactionResponse, dialogDismiss ->
                    dialogDismiss
                    val receipt = transactionResponse.toTransactionReceipt()
                    Log.d("debug transaction data", "$transactionResponse")
                    Log.d("debug transaction receipt", "$receipt")
                    if (receipt.statusName.equals("Successful", true)) {
                        navHostController.navigateToTransactionSuccessRoute(receipt)
                    } else {
                        navHostController.navigateToTransactionFailedRoute(receipt.message)
                    }
                }
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