package com.bankly.feature.eod.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.ui.done.DoneRoute
import com.bankly.core.common.ui.sendreceipt.SendReceiptRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.model.sealed.TransactionReceipt
import com.bankly.feature.eod.ui.dashboard.EodRoute
import com.bankly.feature.eod.ui.eodtransactions.EodTransactionsRoute
import com.bankly.feature.eod.ui.synceod.SyncEodRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val eodNavGraphRoute = "eod_graph"
internal const val eodRoute = eodNavGraphRoute.plus("/eod_route")
internal const val eodTransactionsRoute = eodRoute.plus("/eod_transactions_screen")
internal const val syncEodRoute = eodRoute.plus("/sync_eod_screen")
internal const val doneRoute = eodRoute.plus("/success_screen")

internal fun NavGraphBuilder.eodRoute(
    onBackPress: () -> Unit,
    onSyncEodClick: () -> Unit,
    onViewEodTransactionsClick: () -> Unit,
    onExportFullEodClick: () -> Unit,
) {
    composable(route = eodRoute) {
        EodRoute(
            onBackPress = onBackPress,
            onExportFullEodClick = onExportFullEodClick,
            onSyncEodClick = onSyncEodClick,
            onViewEodTransactionsClick = onViewEodTransactionsClick,
        )
    }
}

internal fun NavGraphBuilder.syncEodRoute(
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
) {
    composable(route = syncEodRoute) {
        SyncEodRoute(
            onBackPress = onBackPress,
            onSessionExpired = onSessionExpired,
        )
    }
}

internal fun NavGraphBuilder.eodTransactionsRoute(
    onBackPress: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit,
) {
    composable(route = eodTransactionsRoute) {
        EodTransactionsRoute(
            onBackPress = onBackPress,
            onGoToTransactionDetailsScreen = onViewTransactionDetailsClick,
        )
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
        it.arguments?.getString(doneTitleArg)?.let { titleString: String ->
            it.arguments?.getString(doneMessageArg)?.let { messageString: String ->
                val title: String = Json.decodeFromString(titleString)
                val message: String = Json.decodeFromString(messageString)
                DoneRoute(
                    title = title,
                    message = message,
                    onDoneClick = onDoneClick,
                )
            }
        }
    }
}
