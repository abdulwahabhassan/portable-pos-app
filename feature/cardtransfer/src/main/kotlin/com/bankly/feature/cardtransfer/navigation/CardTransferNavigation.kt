package com.bankly.feature.cardtransfer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.TransactionType

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
                onBackPress = onBackPress
            )
        }
    }
}

@Composable
fun CardTransferNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit
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
            }
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
            onCloseClick = onBackPress
        )

        enterCardPinRoute(
            onContinueClick = {
                navHostController.navigateToProcessTransactionRoute(
                    TransactionData(
                        TransactionType.BANK_TRANSFER_EXTERNAL,
                        "080999200291",
                        "Hassan Abdulwahab",
                        23000.00,
                        0.00,
                        0.00,
                        "",
                        "",
                        "",
                        AccountNumberType.ACCOUNT_NUMBER,
                        ""
                    )
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        processTransactionRoute(
            onSuccessfulTransaction = {
                navHostController.navigateToTransactionSuccessRoute()
            },
            onFailedTransaction = {
                navHostController.navigateToTransactionFailedRoute()
            }
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = {
                navHostController.navigateToTransactionDetailsRoute()
            },
            onGoHomeClick = onBackPress
        )
        transactionFailedRoute(onGoHomeClick = {})
        transactionDetailsRoute(
            onShareClick = { },
            onSmsClick = { },
            onLogComplaintClick = { },
            onGoToHomeClick = onBackPress
        )
    }

}



