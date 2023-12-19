package com.bankly.feature.cardtransfer.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.model.TransactionData
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
internal const val enterRecipientDetailsRoute =
    cardTransferRoute.plus("/enter_recipient_details_screen")
internal const val selectAccountTypeRoute = cardTransferRoute.plus("/select_account_type_screen")
internal const val processTransactionRoute = cardTransferRoute.plus("/process_transaction_screen")
internal const val transactionSuccessRoute = cardTransferRoute.plus("/transaction_success_screen")
internal const val transactionFailedRoute = cardTransferRoute.plus("/transaction_failed_screen")
internal const val transactionDetailsRoute = cardTransferRoute.plus("/transaction_details_screen")

internal fun NavGraphBuilder.enterRecipientDetailsRoute(
    onBackPress: () -> Unit,
    onContinueClick: (TransactionData) -> Unit,
    onSessionExpired: () -> Unit,
) {
    composable(route = enterRecipientDetailsRoute) {
        RecipientRoute(
            onBackPress = onBackPress,
            onContinueClick = onContinueClick,
            onSessionExpired = onSessionExpired,
        )
    }
}

internal fun NavGraphBuilder.selectAccountTypeRoute(
    onAccountSelected: (AccountType, TransactionData) -> Unit,
    onBackPress: () -> Unit,
    onCancelPress: () -> Unit,
) {
    composable(
        route = "$selectAccountTypeRoute/{$transactionDataArg}",
        arguments = listOf(
            navArgument(transactionDataArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionDataArg)?.let { transactionDataString: String ->
            val transactionData: TransactionData = Json.decodeFromString(transactionDataString)
            SelectAccountTypeRoute(
                onAccountSelected = { accountType: AccountType ->
                    onAccountSelected(accountType, transactionData)
                },
                onBackPress = onBackPress,
                onCancelPress = onCancelPress,
            )
        }
    }
}

internal fun NavGraphBuilder.processTransactionRoute(
    onSuccessfulTransaction: (TransactionReceipt) -> Unit,
    onFailedTransaction: (String, TransactionReceipt?) -> Unit,
    onSessionExpired: () -> Unit,
) {
    composable(
        route = "$processTransactionRoute/{$transactionDataArg}/{$transactionReceiptArg}",
        arguments = listOf(
            navArgument(transactionDataArg) { type = NavType.StringType },
            navArgument(transactionReceiptArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionDataArg)?.let { transactionDataString: String ->
            val transactionData: TransactionData =
                Json.decodeFromString(transactionDataString)
            it.arguments?.getString(transactionReceiptArg)
                ?.let { transactionReceiptString: String ->
                    val transactionReceipt: TransactionReceipt =
                        Json.decodeFromString(transactionReceiptString)
                    ProcessTransactionRoute(
                        transactionData = transactionData,
                        cardTransactionReceipt = transactionReceipt,
                        onTransactionSuccess = onSuccessfulTransaction,
                        onFailedTransaction = onFailedTransaction,
                        onSessionExpired = onSessionExpired,
                    )
                }
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
        it.arguments?.getString(transactionReceiptArg)
            ?.let { transactionReceiptString: String ->
                val transactionReceipt: TransactionReceipt =
                    Json.decodeFromString(transactionReceiptString)
                TransactionSuccessRoute(
                    transactionReceipt = transactionReceipt,
                    message = transactionReceipt.transactionMessage,
                    onViewTransactionDetailsClick = onViewTransactionDetailsClick,
                    onGoToHome = onGoHomeClick,
                )
            }
    }
}

internal fun NavGraphBuilder.transactionFailedRoute(
    onGoHomeClick: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit,
) {
    composable(
        route = "$transactionFailedRoute/{$messageArg}/{$transactionReceiptArg}",
        arguments = listOf(
            navArgument(messageArg) { type = NavType.StringType },
            navArgument(transactionReceiptArg) { type = NavType.StringType },
        ),
    ) { navBackStackEntry ->
        navBackStackEntry.arguments?.getString(messageArg)?.let { messageString: String ->
            val message: String = Json.decodeFromString(messageString)
            val transactionReceipt: TransactionReceipt? =
                navBackStackEntry.arguments?.getString(transactionReceiptArg)
                    ?.let { transactionString: String -> Json.decodeFromString(transactionString) }
            TransactionFailedRoute(
                onGoToHome = onGoHomeClick,
                message = message,
                transactionReceipt = transactionReceipt,
                onViewTransactionDetailsClick = onViewTransactionDetailsClick,
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
        it.arguments?.getString(transactionReceiptArg)
            ?.let { transactionReceiptString: String ->
                val transactionReceipt: TransactionReceipt =
                    Json.decodeFromString(transactionReceiptString)
                TransactionDetailsRoute(
                    transactionReceipt = transactionReceipt,
                    isSuccess = transactionReceipt.isSuccessfulTransaction(),
                    onShareClick = onShareClick,
                    onSmsClick = onSmsClick,
                    onLogComplaintClick = onLogComplaintClick,
                    onGoToHomeClick = onGoToHomeClick,
                )
            }
    }
}
