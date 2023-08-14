package com.bankly.feature.paywithcard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.core.common.ui.entercardpin.EnterCardPinRoute
import com.bankly.core.common.ui.insertcard.InsertCardRoute
import com.bankly.core.common.ui.processTransaction.ProcessTransactionRoute
import com.bankly.core.common.ui.selectaccounttype.SelectAccountTypeRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.common.ui.transactionresponse.TransactionResponseRoute
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.model.AccountType

const val payWithCardNavGraphRoute = "pay_with_card_graph"
internal const val payWithCardRoute = payWithCardNavGraphRoute.plus("/pay_with_card_route")
internal const val selectAccountTypeRoute = payWithCardRoute.plus("/select_account_type_screen")
internal const val insertCardRoute = payWithCardRoute.plus("/insert_card_screen")
internal const val enterPinRoute = payWithCardRoute.plus("/enter_pin_screen")
internal const val processTransactionRoute = payWithCardRoute.plus("/process_transaction_screen")
internal const val transactionResponseRoute = payWithCardRoute.plus("/transaction_response_screen")
internal const val transactionDetailsRoute = payWithCardRoute.plus("/transaction_details_screen")

internal fun NavGraphBuilder.selectAccountTypeRoute(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit
) {
    composable(route = selectAccountTypeRoute) {
        SelectAccountTypeRoute(
            onAccountSelected = onAccountSelected,
            onBackPress = onBackPress
        )
    }
}

internal fun NavGraphBuilder.insertCardRoute(
    onCardInserted: () -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    composable(route = insertCardRoute) {
        InsertCardRoute(
            onCardInserted = onCardInserted,
            onBackPress = onBackPress,
            onCloseClick = onCloseClick
        )
    }
}

internal fun NavGraphBuilder.enterCardPinRoute(
    onContinueClick: (String) -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    composable(route = enterPinRoute) {
        EnterCardPinRoute(
            onContinueClick = onContinueClick,
            onBackPress = onBackPress,
            onCloseClick = onCloseClick
        )
    }
}

internal fun NavGraphBuilder.processTransactionRoute(
    onTransactionProcessed: () -> Unit,
) {
    composable(route = processTransactionRoute) {
        ProcessTransactionRoute(
            onTransactionProcessed = onTransactionProcessed,
        )
    }
}

internal fun NavGraphBuilder.transactionResponseRoute(
    onViewTransactionDetailsClick: () -> Unit,
    onGoHomeClick: () -> Unit
) {
    composable(route = transactionResponseRoute) {
        TransactionResponseRoute(
            onViewTransactionDetailsClick = onViewTransactionDetailsClick,
            onGoHomeClick = onGoHomeClick,
            title = "Transaction Successful",
            subTitle = "Your payment was successful",
            icon = BanklyIcons.Successful
        )
    }
}

internal fun NavGraphBuilder.transactionDetailsRoute(
    onShareClick: () -> Unit,
    onSmsClick: () -> Unit,
    onLogComplaintClick: () -> Unit,
    onGoToHomeClick: () -> Unit
) {
    composable(route = transactionDetailsRoute) {
        TransactionDetailsRoute(
            onShareClick = onShareClick,
            onSmsClick = onSmsClick,
            onLogComplaintClick = onLogComplaintClick,
            onGoToHomeClick = onGoToHomeClick
        )
    }
}
