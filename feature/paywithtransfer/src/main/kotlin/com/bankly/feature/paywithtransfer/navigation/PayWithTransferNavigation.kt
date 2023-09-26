package com.bankly.feature.paywithtransfer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

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
        )
    }
}
