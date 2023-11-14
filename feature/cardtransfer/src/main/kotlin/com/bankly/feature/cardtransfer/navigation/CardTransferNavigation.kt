package com.bankly.feature.cardtransfer.navigation

import ProcessPayment
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.model.TransactionData
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.cardtransfer.util.toTransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools

private const val SUCCESSFUL_STATUS_NAME = "Successful"

fun NavGraphBuilder.cardTransferNavGraph(
    onBackPress: () -> Unit,
) {
    navigation(
        route = cardTransferNavGraphRoute,
        startDestination = cardTransferRoute,
    ) {
        composable(cardTransferRoute) {
            val cardTransferState by rememberCardTransferState()
            CardTransferNavHost(
                navHostController = cardTransferState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun CardTransferNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    val context = LocalContext.current
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = enterRecipientDetailsRoute,
    ) {
        enterRecipientDetailsRoute(
            onBackPress = onBackPress,
            onContinueClick = { transactionData: TransactionData ->
                navHostController.navigateToSelectAccountTypeRoute(transactionData = transactionData)
            },
        )
        selectAccountTypeRoute(
            onAccountSelected = { accountType: AccountType, transactionData: TransactionData ->
                val acctType = when (accountType) {
                    AccountType.SAVINGS -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.SAVINGS
                    AccountType.DEFAULT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.DEFAULT
                    AccountType.CREDIT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CREDIT
                    AccountType.CURRENT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CURRENT
                }
                val transaction = transactionData as TransactionData.CardTransfer
                Log.d("debug transaction data", "trans data: $transaction")
                Tools.SetAccountType(acctType)
                Tools.TransactionAmount = transaction.transactionAmount
                Tools.transactionType =
                    com.bankly.kozonpaymentlibrarymodule.posservices.TransactionType.CASHOUT
                ProcessPayment(context) { transactionResponse, _ ->
                    val receipt = transactionResponse.toTransactionReceipt()
                    if (receipt.statusName.equals(SUCCESSFUL_STATUS_NAME, true)) {
                        navHostController.navigateToProcessTransactionRoute(
                            transaction.copy(
                                responseMessage = transactionResponse.responseMessage ?: "",
                                responseCode = transactionResponse.responseCode ?: ""
                            )
                        )
                    } else {
                        navHostController.navigateToTransactionFailedRoute(receipt.message)
                    }
                }
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCancelPress = onBackPress
        )
        processTransactionRoute(
            onSuccessfulTransaction = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionSuccessRoute(transactionReceipt = transactionReceipt)
            },
            onFailedTransaction = { message: String ->
                navHostController.navigateToTransactionFailedRoute(message = message)
            },
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
