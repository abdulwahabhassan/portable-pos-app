package com.bankly.feature.eod.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bankly.core.model.sealed.TransactionReceipt

fun NavGraphBuilder.eodNavGraph(
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit
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
                onViewTransactionDetailsClick = onViewTransactionDetailsClick
            )
        }
    }
}

@Composable
private fun EodNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit
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
            onViewTransactionDetailsClick = onViewTransactionDetailsClick,
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
