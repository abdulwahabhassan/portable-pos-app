package com.bankly.feature.transactiondetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.ui.done.DoneRoute
import com.bankly.core.common.ui.sendreceipt.SendReceiptRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val transactionDetailsNavGraphRoute = "transaction_details_graph"
internal const val transactionDetailsRoute = transactionDetailsNavGraphRoute.plus("/transaction_details_route")
internal const val sendReceiptRoute = transactionDetailsRoute.plus("/send_receipt_screen")
internal const val doneRoute = transactionDetailsRoute.plus("/success_screen")



internal fun NavGraphBuilder.transactionDetailsRoute(
    transactionReceipt: TransactionReceipt,
    onShareClick: () -> Unit,
    onSmsClick: (TransactionReceipt) -> Unit,
    onLogComplaintClick: () -> Unit,
    onGoToHomeClick: (() -> Unit)? = null,
    onBackPress: () -> Unit
) {
    composable(
        route = transactionDetailsRoute,
    ) {
            TransactionDetailsRoute(
                transactionReceipt = transactionReceipt,
                onShareClick = onShareClick,
                onSmsClick = onSmsClick,
                onLogComplaintClick = onLogComplaintClick,
                onGoToHomeClick = onGoToHomeClick,
                onBackPress = onBackPress
            )
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
            val decodedTransactionReceipt: TransactionReceipt = Json.decodeFromString(encodedTransactionReceipt)
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