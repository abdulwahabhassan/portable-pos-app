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
            onSessionExpired = onSessionExpired,
        )
    }
}