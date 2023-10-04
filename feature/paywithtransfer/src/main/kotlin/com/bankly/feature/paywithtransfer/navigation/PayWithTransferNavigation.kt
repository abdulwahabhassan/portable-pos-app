package com.bankly.feature.paywithtransfer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bankly.core.sealed.TransactionReceipt

fun NavGraphBuilder.payWithTransferNavGraph(
    onBackPress: () -> Unit,
) {
    navigation(
        route = payWithTransferNavGraphRoute,
        startDestination = payWithTransferRoute,
    ) {
        composable(payWithTransferRoute) {
            val payWithTransferState by rememberPayWithTransferState()
            PayWithTransferNavHost(
                navHostController = payWithTransferState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun PayWithTransferNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = payWithTransferRoute,
    ) {
        payWithTransferRoute(
            onBackPress = onBackPress,
            onViewTransactionDetailsClick = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionDetailsRoute(transactionReceipt = transactionReceipt)
            },
            onGoToHomeClick = onBackPress
        )
        transactionDetailsRoute(
            onShareClick = { },
            onSmsClick = { transactionReceipt ->
                navHostController.navigateToSendReceiptRoute(transactionReceipt = transactionReceipt)
            },
            onLogComplaintClick = { },
            onGoToHomeClick = onBackPress,
        )
        sendReceiptRoute(
            onGoToSuccessScreen = {
                navHostController.navigateToDoneRoute(title = "Receipt Sent", message = "Your receipt has been sent")
            },
            onBackPress = {
                navHostController.popBackStack()
            }
        )
        doneRoute(
            onDoneClick = {
                navHostController.popBackStack()
            },
        )
    }
}
