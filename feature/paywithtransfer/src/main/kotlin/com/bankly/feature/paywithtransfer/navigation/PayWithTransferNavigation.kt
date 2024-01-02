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
    onSessionExpired: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit
) {
    navigation(
        route = payWithTransferNavGraphRoute,
        startDestination = payWithTransferHomeRoute,
    ) {
        composable(payWithTransferHomeRoute) {
            val payWithTransferState by rememberPayWithTransferState()
            PayWithTransferNavHost(
                navHostController = payWithTransferState.navHostController,
                onBackPress = onBackPress,
                onSessionExpired = onSessionExpired,
                onViewTransactionDetailsClick = onViewTransactionDetailsClick
            )
        }
    }
}

@Composable
private fun PayWithTransferNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = payWithTransferHomeRoute,
    ) {
        payWithTransferRoute(
            onBackPress = onBackPress,
            onViewTransactionDetailsClick = onViewTransactionDetailsClick,
            onGoToHomeClick = onBackPress,
            onSessionExpired = onSessionExpired,
        )
    }
}
