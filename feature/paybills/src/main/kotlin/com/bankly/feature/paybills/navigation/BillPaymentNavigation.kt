package com.bankly.feature.paybills.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bankly.core.common.model.TransactionData
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.paybills.model.BillType

fun NavGraphBuilder.billPaymentNavGraph(
    onBackPress: () -> Unit,
    onForgotPinClick: () -> Unit,
) {
    navigation(
        route = billPaymentNavGraphRoute,
        startDestination = billPaymentRoute,
    ) {
        composable(billPaymentRoute) {
            val sendMoneyState by rememberBillPaymentState()
            BillPaymentNavHost(
                navHostController = sendMoneyState.navHostController,
                onBackPress = onBackPress,
                onForgotPinClick = onForgotPinClick,

            )
        }
    }
}

@Composable
private fun BillPaymentNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onForgotPinClick: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = selectBillTypeRoute,
    ) {
        selectBillTypeRoute(
            onBillTypeSelected = { billType: BillType ->
                navHostController.navigateToBeneficiaryRoute(billType)
            },
            onBackPress = onBackPress,
        )
        beneficiaryRoute(
            onContinueClick = { transactionData: TransactionData ->
                navHostController.navigateToConfirmTransactionRoute(
                    transactionData = transactionData,
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress,
        )
        confirmTransactionRoute(
            onConfirmationSuccess = { transactionData: TransactionData ->
                navHostController.navigateToProcessTransactionRoute(transactionData = transactionData)
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress,
            onForgotPinClick = onForgotPinClick,
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