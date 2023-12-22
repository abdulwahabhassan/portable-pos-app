package com.bankly.feature.sendmoney.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.model.TransactionData
import com.bankly.core.sealed.TransactionReceipt

fun NavGraphBuilder.sendMoneyNavGraph(
    onBackPress: () -> Unit,
    onForgotPinClick: () -> Unit,
    onSessionExpired: () -> Unit,
) {
    navigation(
        route = sendMoneyNavGraphRoute,
        startDestination = sendMoneyRoute,
    ) {
        composable(sendMoneyRoute) {
            val sendMoneyState by rememberSendMoneyState()
            SendMoneyNavHost(
                navHostController = sendMoneyState.navHostController,
                onBackPress = onBackPress,
                onForgotPinClick = onForgotPinClick,
                onSessionExpired = onSessionExpired,
            )
        }
    }
}

@Composable
private fun SendMoneyNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onForgotPinClick: () -> Unit,
    onSessionExpired: () -> Unit,
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
