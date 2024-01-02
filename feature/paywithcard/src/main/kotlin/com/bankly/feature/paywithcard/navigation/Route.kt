package com.bankly.feature.paywithcard.navigation

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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val payWithCardNavGraphRoute = "pay_with_card_graph"
internal const val payWithCardRoute = payWithCardNavGraphRoute.plus("/pay_with_card_route")
internal const val selectAccountTypeRoute = payWithCardRoute.plus("/select_account_type_screen")
internal const val transactionSuccessRoute = payWithCardRoute.plus("/transaction_success_screen")
internal const val transactionFailedRoute = payWithCardRoute.plus("/transaction_failed_screen")

internal fun NavGraphBuilder.selectAccountTypeRoute(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit,
    onCancelPress: () -> Unit,
) {
    composable(route = selectAccountTypeRoute) {
        SelectAccountTypeRoute(
            onAccountSelected = onAccountSelected,
            onBackPress = onBackPress,
            onCancelPress = onCancelPress,
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
        it.arguments?.getString(transactionReceiptArg)?.let { transactionReceiptString: String ->
            val transactionReceipt: TransactionReceipt = Json.decodeFromString(transactionReceiptString)
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

