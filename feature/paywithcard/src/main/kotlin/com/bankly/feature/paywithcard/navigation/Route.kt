package com.bankly.feature.paywithcard.navigation

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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val payWithCardNavGraphRoute = "pay_with_card_graph"
internal const val payWithCardRoute = payWithCardNavGraphRoute.plus("/pay_with_card_route")
internal const val selectAccountTypeRoute = payWithCardRoute.plus("/select_account_type_screen")
internal const val transactionSuccessRoute = payWithCardRoute.plus("/transaction_success_screen")
internal const val transactionFailedRoute = payWithCardRoute.plus("/transaction_failed_screen")
internal const val transactionDetailsRoute = payWithCardRoute.plus("/transaction_details_screen")

internal fun NavGraphBuilder.selectAccountTypeRoute(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit,
    onCancelPress: () -> Unit,
) {
    composable(route = selectAccountTypeRoute) {
        SelectAccountTypeRoute(
            onAccountSelected = onAccountSelected,
            onBackPress = onBackPress,
            onCancelPress = onCancelPress
        )
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
