package com.bankly.feature.sendmoney.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bankly.feature.sendmoney.model.SendMoneyChannel

fun NavGraphBuilder.sendMoneyNavGraph(
    onBackPress: () -> Unit
) {
    navigation(
        route = sendMoneyNavGraphRoute,
        startDestination = sendMoneyRoute
    ) {
        composable(sendMoneyRoute) {
            val sendMoneyState by rememberSendMoneyState()
            SendMoneyNavHost(
                navHostController = sendMoneyState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
fun SendMoneyNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = selectChannelRoute,
    ) {
        selectChannelRoute(
            onSendMoneyChannelSelected = { channel: SendMoneyChannel ->
                navHostController.navigateToBeneficiaryRoute(channel)
            },
            onBackPress = onBackPress,
        )
        beneficiaryRoute(
            onContinueClick = {
                navHostController.navigateToConfirmTransactionRoute()
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        confirmTransactionRoute(
            onConfirmationSuccess = {
                navHostController.navigateToProcessTransactionRoute()
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress,
        )
        processTransactionRoute(
            onTransactionProcessed = {
                navHostController.navigateToTransactionResponseRoute()
            }
        )
        transactionResponseRoute(
            onViewTransactionDetailsClick = {
                navHostController.navigateToTransactionDetailsRoute()
            },
            onGoHomeClick = onBackPress
        )
        transactionDetailsRoute(
            onShareClick = { },
            onSmsClick = { },
            onLogComplaintClick = { },
            onGoToHomeClick = onBackPress
        )
    }
}