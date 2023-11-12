package com.bankly.feature.checkcardbalance.navigation

import ProcessPayment
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bankly.core.common.model.AccountType
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools

fun NavGraphBuilder.checkCardBalanceNavGraph(
    onBackPress: () -> Unit,
) {
    Tools.transactionType = com.bankly.kozonpaymentlibrarymodule.posservices.TransactionType.BALANCE_INQUIRY

    navigation(
        route = "$checkCardBalanceNavGraphRoute/{$amountArg}",
        startDestination = checkCardBalanceRoute,
        arguments = listOf(
            navArgument(amountArg) { type = NavType.StringType },
        ),
    ) {
        composable(checkCardBalanceRoute) {
            val checkCardBalanceState by rememberCheckCardBalanceState()
            CheckCardBalanceNavHost(
                navHostController = checkCardBalanceState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun CheckCardBalanceNavHost(
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
                Tools.TransactionAmount = 20.00
                Tools.SetAccountType(acctType)
                ProcessPayment(context) { transactionResponse, _ ->
//                    val receipt = transactionResponse.toTransactionReceipt()
                    Log.d("debug transaction data", "$transactionResponse")
//                    Log.d("debug transaction receipt", "$receipt")
//                    if (receipt.statusName.equals("Successful", true)) {
//                        navHostController.navigateToTransactionSuccessRoute(receipt)
//                    } else {
//                        navHostController.navigateToTransactionFailedRoute(receipt.message)
//                    }
                }
            },
            onBackPress = onBackPress,
        )

//        insertCardRoute(
//            onCardInserted = {
//                navHostController.navigateToEnterPinRoute()
//            },
//            onBackPress = {
//                navHostController.popBackStack()
//            },
//            onCloseClick = onBackPress,
//        )
//        enterCardPinRoute(
//            onContinueClick = {
//                navHostController.navigateToProcessTransactionRoute(
//                    TransactionData.mockCardWithdrawalTransactionData(),
//                )
//            },
//            onBackPress = {
//                navHostController.popBackStack()
//            },
//            onCloseClick = onBackPress,
//        )
//        processTransactionRoute(
//            onSuccessfulTransaction = { transactionReceipt: TransactionReceipt ->
//                navHostController.navigateToTransactionSuccessRoute(transactionReceipt = transactionReceipt)
//            },
//            onFailedTransaction = { message: String ->
//                navHostController.navigateToTransactionFailedRoute(message = message)
//            },
//        )
        cardBalanceRoute(
            onGoToDashboardClick = onBackPress
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionDetailsRoute(transactionReceipt = transactionReceipt)
            },
            onGoHomeClick = onBackPress,
        )
        transactionFailedRoute(
            onGoHomeClick = onBackPress,
        )
        transactionDetailsRoute(
            onShareClick = { },
            onSmsClick = { },
            onLogComplaintClick = { },
            onGoToHomeClick = onBackPress,
        )
    }
}
