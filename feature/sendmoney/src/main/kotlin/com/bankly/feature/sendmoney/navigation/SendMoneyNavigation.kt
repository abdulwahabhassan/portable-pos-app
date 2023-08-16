package com.bankly.feature.sendmoney.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.sealed.Transaction

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
            onContinueClick = { transactionData: TransactionData ->
                navHostController.navigateToConfirmTransactionRoute(
                    transactionData = transactionData
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        confirmTransactionRoute(
            onConfirmationSuccess = { transactionData: TransactionData ->
                navHostController.navigateToProcessTransactionRoute(transactionData = transactionData)
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress,
        )
        processTransactionRoute(
            onSuccessfulTransaction = { transaction: Transaction ->
                navHostController.navigateToTransactionSuccessRoute(transaction = transaction)
            },
            onFailedTransaction = { message: String ->
                navHostController.navigateToTransactionFailedRoute(message = message)
            }
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = { transaction: Transaction ->
                navHostController.navigateToTransactionDetailsRoute(transaction = transaction)
            },
            onGoHomeClick = onBackPress
        )
        transactionFailedRoute(
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