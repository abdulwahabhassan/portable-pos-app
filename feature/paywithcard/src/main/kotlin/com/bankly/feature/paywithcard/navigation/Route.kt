package com.bankly.feature.paywithcard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.core.designsystem.R
import com.bankly.feature.paywithcard.model.AccountType
import com.bankly.feature.paywithcard.ui.entercardpin.EnterCardPinRoute
import com.bankly.feature.paywithcard.ui.insertcard.InsertCardRoute
import com.bankly.feature.paywithcard.ui.selectaccounttype.SelectAccountTypeRoute
import com.bankly.feature.paywithcard.ui.transactionresponse.TransactionResponseRoute

const val payWithCardNavGraphRoute = "pay_with_card_graph"
internal const val payWithCardRoute = payWithCardNavGraphRoute.plus("/pay_with_card_route")
internal const val selectAccountTypeRoute = payWithCardRoute.plus("/select_account_type_screen")
internal const val insertCardRoute = payWithCardRoute.plus("/insert_card_screen")
internal const val enterPinRoute = payWithCardRoute.plus("/enter_pin_screen")
internal const val transactionResponseRoute = payWithCardRoute.plus("/transaction_response_screen")

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

internal fun NavGraphBuilder.transactionResponseRoute(
    onViewTransactionDetailsClick: () -> Unit,
    onGoHomeClick: () -> Unit
) {
    composable(route = transactionResponseRoute) {
        TransactionResponseRoute(
            onViewTransactionDetailsClick = onViewTransactionDetailsClick,
            onGoHomeClick = onGoHomeClick,
            title = "Transaction Failed",
            subTitle = "Insufficient funds",
            icon = R.drawable.ic_transaction_failed
        )
    }
}