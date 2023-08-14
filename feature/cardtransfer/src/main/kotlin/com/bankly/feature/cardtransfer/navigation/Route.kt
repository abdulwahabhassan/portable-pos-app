package com.bankly.feature.cardtransfer.navigation

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
import com.bankly.feature.cardtransfer.ui.recipientdetails.EnterRecipientDetailsRoute

const val cardTransferNavGraphRoute = "card_transfer_nav_graph"
internal const val cardTransferRoute = cardTransferNavGraphRoute.plus("/card_transfer_route")
internal const val enterRecipientDetailsRoute = cardTransferRoute.plus("/enter_recipient_details_screen")
internal const val selectAccountTypeRoute = cardTransferRoute.plus("/select_account_type_screen")
internal const val insertCardRoute = cardTransferRoute.plus("/insert_card_screen")
internal const val enterPinRoute = cardTransferRoute.plus("/enter_pin_screen")
internal const val processTransactionRoute = cardTransferRoute.plus("/process_transaction_screen")
internal const val transactionResponseRoute = cardTransferRoute.plus("/transaction_response_screen")
internal const val transactionDetailsRoute = cardTransferRoute.plus("/transaction_details_screen")

internal fun NavGraphBuilder.enterRecipientDetailsRoute(
    onBackPress: () -> Unit,
    onContinueClick: () -> Unit
) {
    composable(route = enterRecipientDetailsRoute) {
        EnterRecipientDetailsRoute(
            onBackPress = onBackPress,
            onContinueClick = onContinueClick
        )
    }
}

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