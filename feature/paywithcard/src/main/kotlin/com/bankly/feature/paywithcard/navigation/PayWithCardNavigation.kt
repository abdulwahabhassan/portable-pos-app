package com.bankly.feature.paywithcard.navigation

import ProcessPayment
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.paywithcard.util.toTransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools

private const val SUCCESSFUL_STATUS_NAME = "Successful"

fun NavGraphBuilder.payWithCardNavGraph(
    onBackPress: () -> Unit,
    appNavController: NavHostController,
) {
    navigation(
        route = "$payWithCardNavGraphRoute/{$amountArg}",
        startDestination = payWithCardRoute,
        arguments = listOf(
            navArgument(amountArg) { type = NavType.StringType },
        ),
    ) {
        composable(payWithCardRoute) { backStackEntry: NavBackStackEntry ->
            val parentEntry = remember(backStackEntry) {
                appNavController.getBackStackEntry(payWithCardRoute)
            }
            val amount = parentEntry.arguments?.getString(
                amountArg,
            )?.toDouble() ?: 0.00
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
                val acctType = when (accountType) {
                    AccountType.SAVINGS -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.SAVINGS
                    AccountType.DEFAULT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.DEFAULT
                    AccountType.CREDIT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CREDIT
                    AccountType.CURRENT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CURRENT
                }
                Tools.SetAccountType(acctType)
                Tools.transactionType =
                    com.bankly.kozonpaymentlibrarymodule.posservices.TransactionType.CASHOUT
                ProcessPayment(context) { transactionResponse, _ ->
                    Log.d("debug transaction response", "$transactionResponse")
                    val receipt = transactionResponse.toTransactionReceipt()
                    if (receipt.statusName.equals(SUCCESSFUL_STATUS_NAME, true)) {
                        navHostController.navigateToTransactionSuccessRoute(receipt)
                    } else {
                        navHostController.navigateToTransactionFailedRoute(receipt.message, receipt)
                    }
                }
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCancelPress = onBackPress
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionDetailsRoute(transactionReceipt = transactionReceipt)
            },
            onGoHomeClick = onBackPress,
        )
        transactionFailedRoute(
            onGoHomeClick = onBackPress,
            onViewTransactionDetailsClick = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionDetailsRoute(transactionReceipt = transactionReceipt)
            },
        )
        transactionDetailsRoute(
            onShareClick = { },
            onSmsClick = { },
            onLogComplaintClick = { },
            onGoToHomeClick = onBackPress,
        )
    }
}
