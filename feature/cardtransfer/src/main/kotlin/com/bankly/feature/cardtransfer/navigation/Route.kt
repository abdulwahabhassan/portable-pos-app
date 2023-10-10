package com.bankly.feature.cardtransfer.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.ui.entercardpin.EnterCardPinRoute
import com.bankly.core.common.ui.insertcard.InsertCardRoute
import com.bankly.core.common.ui.processtransaction.ProcessTransactionRoute
import com.bankly.core.common.ui.selectaccounttype.SelectAccountTypeRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.common.ui.transactionfailed.TransactionFailedRoute
import com.bankly.core.common.ui.transactionsuccess.TransactionSuccessRoute
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.cardtransfer.ui.recipient.RecipientRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val cardTransferNavGraphRoute = "card_transfer_nav_graph"
internal const val cardTransferRoute = cardTransferNavGraphRoute.plus("/card_transfer_route")
internal const val enterRecipientDetailsRoute = cardTransferRoute.plus("/enter_recipient_details_screen")
internal const val selectAccountTypeRoute = cardTransferRoute.plus("/select_account_type_screen")
internal const val insertCardRoute = cardTransferRoute.plus("/insert_card_screen")
internal const val enterPinRoute = cardTransferRoute.plus("/enter_pin_screen")
internal const val processTransactionRoute = cardTransferRoute.plus("/process_transaction_screen")
internal const val transactionSuccessRoute = cardTransferRoute.plus("/transaction_success_screen")
internal const val transactionFailedRoute = cardTransferRoute.plus("/transaction_failed_screen")
internal const val transactionDetailsRoute = cardTransferRoute.plus("/transaction_details_screen")

internal fun NavGraphBuilder.enterRecipientDetailsRoute(
    onBackPress: () -> Unit,
    onContinueClick: (TransactionData) -> Unit,
) {
    composable(route = enterRecipientDetailsRoute) {
        RecipientRoute(
            onBackPress = onBackPress,
            onContinueClick = onContinueClick,
        )
    }
}

internal fun NavGraphBuilder.selectAccountTypeRoute(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit,
) {
    composable(route = selectAccountTypeRoute) {
        SelectAccountTypeRoute(
            onAccountSelected = onAccountSelected,
            onBackPress = onBackPress,
        )
    }
}

internal fun NavGraphBuilder.insertCardRoute(
    onCardInserted: () -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
) {
    composable(route = insertCardRoute) {
        InsertCardRoute(
            onCardInserted = onCardInserted,
            onBackPress = onBackPress,
            onCloseClick = onCloseClick,
        )
    }
}

internal fun NavGraphBuilder.enterCardPinRoute(
    onContinueClick: (String) -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
) {
    composable(route = enterPinRoute) {
        EnterCardPinRoute(
            onContinueClick = onContinueClick,
            onBackPress = onBackPress,
            onCloseClick = onCloseClick,
        )
    }
}

internal fun NavGraphBuilder.processTransactionRoute(
    onSuccessfulTransaction: (TransactionReceipt) -> Unit,
    onFailedTransaction: (String) -> Unit,
) {
    composable(
        route = "$processTransactionRoute/{$transactionDataArg}",
        arguments = listOf(
            navArgument(transactionDataArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionDataArg)?.let { transactionData: String ->
            val data: TransactionData = Json.decodeFromString(transactionData)
            ProcessTransactionRoute(
                transactionData = data,
                onTransactionSuccess = onSuccessfulTransaction,
                onFailedTransaction = onFailedTransaction,
            )
        }
    }
}

internal fun NavGraphBuilder.transactionSuccessRoute(
    onGoHomeClick: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit,
) {
    composable(
        route = "$transactionSuccessRoute/{$transactionReceiptArg}",
        arguments = listOf(
            navArgument(transactionReceiptArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionReceiptArg)?.let { transaction: String ->
            val trans: TransactionReceipt = Json.decodeFromString(transaction)
            TransactionSuccessRoute(
                transactionReceipt = trans,
                message = trans.transactionMessage,
                onViewTransactionDetailsClick = onViewTransactionDetailsClick,
                onGoToHome = onGoHomeClick,
            )
        }
    }
}

internal fun NavGraphBuilder.transactionFailedRoute(
    onGoHomeClick: () -> Unit,
) {
    composable(
        route = "$transactionFailedRoute/{$messageArg}",
        arguments = listOf(
            navArgument(messageArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(messageArg)?.let { message: String ->
            TransactionFailedRoute(
                onGoToHome = onGoHomeClick,
                message = message,
            )
        }
    }
}

internal fun NavGraphBuilder.transactionDetailsRoute(
    onShareClick: () -> Unit,
    onSmsClick: (TransactionReceipt) -> Unit,
    onLogComplaintClick: () -> Unit,
    onGoToHomeClick: () -> Unit,
) {
    composable(
        route = "$transactionDetailsRoute/{$transactionReceiptArg}",
        arguments = listOf(
            navArgument(transactionReceiptArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionReceiptArg)?.let { transaction: String ->
            val trans: TransactionReceipt = Json.decodeFromString(transaction)
            TransactionDetailsRoute(
                transactionReceipt = trans,
                onShareClick = onShareClick,
                onSmsClick = onSmsClick,
                onLogComplaintClick = onLogComplaintClick,
                onGoToHomeClick = onGoToHomeClick,
            )
        }
    }
}
