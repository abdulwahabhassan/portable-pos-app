package com.bankly.feature.paywithtransfer.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.feature.paywithtransfer.ui.PayWithTransferRoute

const val payWithTransferNavGraphRoute = "pay_with_transfer_graph"
internal const val payWithTransferRoute = payWithTransferNavGraphRoute.plus("/pay_with_transfer_route")

internal fun NavGraphBuilder.payWithTransferRoute(
    onBackPress: () -> Unit,
) {
    composable(route = payWithTransferRoute) {
        PayWithTransferRoute(
            onBackPress = onBackPress,
        )
    }
}
