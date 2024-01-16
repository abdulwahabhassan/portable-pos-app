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
import com.bankly.core.model.sealed.TransactionReceipt
import com.bankly.feature.paybills.model.BillType

fun NavGraphBuilder.billPaymentNavGraph(
    onBackPress: () -> Unit,
    onForgotPinClick: () -> Unit,
    onSessionExpired: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit
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
                onSessionExpired = onSessionExpired,
                onViewTransactionDetailsClick = onViewTransactionDetailsClick
            )
        }
    }
}

@Composable
private fun BillPaymentNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onForgotPinClick: () -> Unit,
    onSessionExpired: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit
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
            onSessionExpired = onSessionExpired,
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
            onSuccessfulTransaction = { _, _, transactionReceipt ->
                navHostController.navigateToTransactionSuccessRoute(transactionReceipt = transactionReceipt)
            },
            onFailedTransaction = { _, _, message ->
                navHostController.navigateToTransactionFailedRoute(message = message)
            },
            onSessionExpired = onSessionExpired,
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = onViewTransactionDetailsClick,
            onGoHomeClick = onBackPress,
        )
        transactionFailedRoute(
            onGoHomeClick = onBackPress,
        )
    }
}
