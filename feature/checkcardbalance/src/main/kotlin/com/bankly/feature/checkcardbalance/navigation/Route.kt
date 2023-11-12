package com.bankly.feature.checkcardbalance.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.ui.selectaccounttype.SelectAccountTypeRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.common.ui.transactionfailed.TransactionFailedRoute
import com.bankly.core.common.ui.transactionsuccess.TransactionSuccessRoute
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.checkcardbalance.ui.CardBalanceRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val checkCardBalanceNavGraphRoute = "check_card_balance_graph"
internal const val checkCardBalanceRoute =
    checkCardBalanceNavGraphRoute.plus("/check_card_balance_route")
internal const val selectAccountTypeRoute =
    checkCardBalanceRoute.plus("/select_account_type_screen")
internal const val cardBalanceRoute = checkCardBalanceRoute.plus("/card_balance_screen")

//internal const val insertCardRoute = payWithCardRoute.plus("/insert_card_screen")
//internal const val enterPinRoute = payWithCardRoute.plus("/enter_pin_screen")
//internal const val processTransactionRoute = payWithCardRoute.plus("/process_transaction_screen")
internal const val transactionSuccessRoute =
    checkCardBalanceRoute.plus("/transaction_success_screen")
internal const val transactionFailedRoute = checkCardBalanceRoute.plus("/transaction_failed_screen")
internal const val transactionDetailsRoute =
    checkCardBalanceRoute.plus("/transaction_details_screen")

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

//internal fun NavGraphBuilder.insertCardRoute(
//    onCardInserted: () -> Unit,
//    onBackPress: () -> Unit,
//    onCloseClick: () -> Unit,
//) {
//    composable(route = insertCardRoute) {
//        InsertCardRoute(
//            onCardInserted = onCardInserted,
//            onBackPress = onBackPress,
//            onCloseClick = onCloseClick,
//        )
//    }
//}
//
//internal fun NavGraphBuilder.enterCardPinRoute(
//    onContinueClick: (String) -> Unit,
//    onBackPress: () -> Unit,
//    onCloseClick: () -> Unit,
//) {
//    composable(route = enterPinRoute) {
//        EnterCardPinRoute(
//            onContinueClick = onContinueClick,
//            onBackPress = onBackPress,
//            onCloseClick = onCloseClick,
//        )
//    }
//}
//
//internal fun NavGraphBuilder.processTransactionRoute(
//    onSuccessfulTransaction: (TransactionReceipt) -> Unit,
//    onFailedTransaction: (String) -> Unit,
//) {
//    composable(
//        route = "$processTransactionRoute/{$transactionDataArg}",
//        arguments = listOf(
//            navArgument(transactionDataArg) { type = NavType.StringType },
//        ),
//    ) {
//        it.arguments?.getString(transactionDataArg)?.let { transactionData: String ->
//            val data: TransactionData = Json.decodeFromString(transactionData)
//            ProcessTransactionRoute(
//                transactionData = data,
//                onTransactionSuccess = onSuccessfulTransaction,
//                onFailedTransaction = onFailedTransaction,
//            )
//        }
//    }
//}
//
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

internal fun NavGraphBuilder.cardBalanceRoute(
    onGoToDashboardClick: () -> Unit
) {
    composable(
        route = "$cardBalanceRoute/{$cardBalanceAmountArg}",
        arguments = listOf(
            navArgument(cardBalanceAmountArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(cardBalanceAmountArg)?.let { transaction: String ->
            val amount: String = Json.decodeFromString(transaction)
            CardBalanceRoute(
                amount = amount,
                onGoToDashboardClick = onGoToDashboardClick)
        }
    }
}
