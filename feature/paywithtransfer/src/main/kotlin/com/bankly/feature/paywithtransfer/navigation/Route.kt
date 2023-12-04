package com.bankly.feature.paywithtransfer.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.ui.done.DoneRoute
import com.bankly.core.common.ui.sendreceipt.SendReceiptRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.paywithtransfer.ui.PayWithTransferRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val payWithTransferNavGraphRoute = "pay_with_transfer_graph"
internal const val payWithTransferRoute =
    payWithTransferNavGraphRoute.plus("/pay_with_transfer_route")
internal const val payWithTransferHomeRoute =
    payWithTransferRoute.plus("/pay_with_transfer_home_screen")
internal const val transactionDetailsRoute =
    payWithTransferRoute.plus("/transaction_details_screen")
internal const val sendReceiptRoute = payWithTransferRoute.plus("/send_receipt_screen")
internal const val doneRoute = payWithTransferRoute.plus("/success_screen")

internal fun NavGraphBuilder.payWithTransferRoute(
    onBackPress: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit,
    onGoToHomeClick: () -> Unit,
    onSessionExpired: () -> Unit,
) {
    composable(route = payWithTransferHomeRoute) {
        PayWithTransferRoute(
            onBackPress = onBackPress,
            onViewTransactionDetailsClick = onViewTransactionDetailsClick,
            onGoToHomeClick = onGoToHomeClick,
            onSessionExpired = onSessionExpired
        )
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
        it.arguments?.getString(transactionReceiptArg)?.let { transactionReceipt: String ->
            val receipt: TransactionReceipt = Json.decodeFromString(transactionReceipt)
            TransactionDetailsRoute(
                transactionReceipt = receipt,
                onShareClick = onShareClick,
                onSmsClick = onSmsClick,
                onLogComplaintClick = onLogComplaintClick,
                onGoToHomeClick = onGoToHomeClick,
            )
        }
    }
}

internal fun NavGraphBuilder.sendReceiptRoute(
    onGoToSuccessScreen: (String) -> Unit,
    onBackPress: () -> Unit,
) {
    composable(
        route = "$sendReceiptRoute/{$transactionReceiptArg}",
        arguments = listOf(
            navArgument(transactionReceiptArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionReceiptArg)?.let { encodedTransactionReceipt: String ->
            val decodedTransactionReceipt: TransactionReceipt =
                Json.decodeFromString(encodedTransactionReceipt)
            SendReceiptRoute(
                transactionReceipt = decodedTransactionReceipt,
                onGoToSuccessScreen = onGoToSuccessScreen,
                onBackPress = onBackPress
            )
        }
    }
}

internal fun NavGraphBuilder.doneRoute(
    onDoneClick: () -> Unit,
) {
    composable(
        route = "$doneRoute/{$doneTitleArg}/{$doneMessageArg}",
        arguments = listOf(
            navArgument(doneTitleArg) { type = NavType.StringType },
            navArgument(doneMessageArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(doneTitleArg)?.let { encodedTitle: String ->
            it.arguments?.getString(doneMessageArg)?.let { encodedMessage: String ->
                val decodedTitle: String = Json.decodeFromString(encodedTitle)
                val decodedMessage: String = Json.decodeFromString(encodedMessage)
                DoneRoute(
                    title = decodedTitle,
                    message = decodedMessage,
                    onDoneClick = onDoneClick,
                )
            }
        }
    }
}