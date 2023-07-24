package com.bankly.feature.paywithcard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.payWithCardNavGraph(
    onBackPress: () -> Unit
) {
    navigation(
        route = payWithCardNavGraphRoute,
        startDestination = payWithCardRoute,
    ) {
        composable(payWithCardRoute) {
            val payWithCardState by rememberPayWithCardState()
            PayWithCardNavHost(
                navHostController = payWithCardState.navHostController,
                onBackPress = onBackPress
            )
        }
    }
}

@Composable
fun PayWithCardNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = selectAccountTypeRoute,
    ) {
        selectAccountTypeRoute(
            onAccountSelected = {
                navHostController.navigateToInsertCardRoute()
            },
            onBackPress = onBackPress
        )
        insertCardRoute(
            onCardInserted = {
                navHostController.navigateToEnterPinRoute()
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        enterCardPinRoute(
            onContinueClick = {
                navHostController.navigateToTransactionResponseRoute()
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        transactionResponseRoute(
            onViewTransactionDetailsClick = {},
            onGoHomeClick = onBackPress
        )
    }
}