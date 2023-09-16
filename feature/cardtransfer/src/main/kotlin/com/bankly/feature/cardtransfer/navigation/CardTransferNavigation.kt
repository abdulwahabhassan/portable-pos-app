package com.bankly.feature.cardtransfer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.TransactionType
import com.bankly.core.sealed.TransactionReceipt

fun NavGraphBuilder.cardTransferNavGraph(
    onBackPress: () -> Unit,
) {
    navigation(
        route = cardTransferNavGraphRoute,
        startDestination = cardTransferRoute,
    ) {
        composable(cardTransferRoute) {
            val cardTransferState by rememberCardTransferState()
            CardTransferNavHost(
                navHostController = cardTransferState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun CardTransferNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = enterRecipientDetailsRoute,
    ) {
        enterRecipientDetailsRoute(
            onBackPress = onBackPress,
            onContinueClick = {
                navHostController.navigateToSelectAccountTypeRoute()
            },
        )
        selectAccountTypeRoute(
            onAccountSelected = { accountType: AccountType ->
                navHostController.navigateToInsertCardRoute()
            },
            onBackPress = onBackPress,
        )
        insertCardRoute(
            onCardInserted = {
                navHostController.navigateToEnterPinRoute()
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress,
        )

        enterCardPinRoute(
            onContinueClick = {
                navHostController.navigateToProcessTransactionRoute(
                    TransactionData.mockTransactionData()
                        .copy(transactionType = TransactionType.CARD_WITHDRAWAL),
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress,
        )
        processTransactionRoute(
            onSuccessfulTransaction = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionSuccessRoute(transactionReceipt = transactionReceipt)
            },
            onFailedTransaction = { message: String ->
                navHostController.navigateToTransactionFailedRoute(message = message)
            },
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
