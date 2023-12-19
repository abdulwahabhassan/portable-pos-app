package com.bankly.feature.eod.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bankly.core.sealed.TransactionReceipt

fun NavGraphBuilder.eodNavGraph(
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
) {
    navigation(
        route = eodNavGraphRoute,
        startDestination = eodRoute,
    ) {
        composable(eodRoute) {
            val eodState by rememberEodState()
            EodNavHost(
                navHostController = eodState.navHostController,
                onBackPress = onBackPress,
                onSessionExpired = onSessionExpired,
            )
        }
    }
}

@Composable
private fun EodNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = eodRoute,
    ) {
        eodRoute(
            onBackPress = onBackPress,
            onExportFullEodClick = {
                navHostController.navigateToExportSuccessfulRoute(
                    title = "Export Successful",
                    message = "Kindly check your email to view EOD report",
                )
            },
            onSyncEodClick = {
                navHostController.navigateToSyncEodRoute()
            },
            onViewEodTransactionsClick = {
                navHostController.navigateToEodTransactionsRoute()
            },
        )
        eodTransactionsRoute(
            onBackPress = {
                navHostController.popBackStack()
            },
            onViewTransactionDetailsClick = { receipt: TransactionReceipt ->
                navHostController.navigateToTransactionDetailsRoute(receipt)
            },
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
                navHostController.navigateToReceiptSuccessfullySentRoute(
                    title = "Receipt Sent",
                    message = "Your receipt has been sent",
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
        )
        doneRoute(
            onDoneClick = {
                navHostController.popBackStack()
            },
        )
        syncEodRoute(
            onBackPress = {
                navHostController.popBackStack()
            },
            onSessionExpired = onSessionExpired,
        )
    }
}
