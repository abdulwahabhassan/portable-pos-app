package com.bankly.feature.checkcardbalance.navigation

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
import com.bankly.feature.checkcardbalance.ui.CardBalanceRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val checkCardBalanceNavGraphRoute = "check_card_balance_graph"
internal const val checkCardBalanceRoute =
    checkCardBalanceNavGraphRoute.plus("/check_card_balance_route")
internal const val selectAccountTypeRoute =
    checkCardBalanceRoute.plus("/select_account_type_screen")
internal const val cardBalanceRoute = checkCardBalanceRoute.plus("/card_balance_screen")

internal fun NavGraphBuilder.selectAccountTypeRoute(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit,
) {
    composable(route = selectAccountTypeRoute) {
        SelectAccountTypeRoute(
            onAccountSelected = onAccountSelected,
            onBackPress = onBackPress,
        )
    }
}

internal fun NavGraphBuilder.cardBalanceRoute(
    onGoToDashboardClick: () -> Unit
) {
    composable(
        route = "$cardBalanceRoute/{$cardBalanceAmountArg}/{$cardBalanceResponseCodeArg}/{$cardBalanceResponseMessageArg}",
        arguments = listOf(
            navArgument(cardBalanceAmountArg) { type = NavType.StringType },
            navArgument(cardBalanceResponseCodeArg) { type = NavType.StringType },
            navArgument(cardBalanceResponseMessageArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(cardBalanceAmountArg)?.let { cardBalanceAmount: String ->
            it.arguments?.getString(cardBalanceResponseCodeArg)
                ?.let { cardBalanceResponseCode: String ->
                    it.arguments?.getString(cardBalanceResponseMessageArg)
                        ?.let { cardBalanceResponseMessage: String ->
                            val amount: String = Json.decodeFromString(cardBalanceAmount)
                            val responseCode: String =
                                Json.decodeFromString(cardBalanceResponseCode)
                            val responseMessage: String = Json.decodeFromString(cardBalanceResponseMessage)
                            CardBalanceRoute(
                                amount = amount,
                                responseCode = responseCode,
                                responseMessage = responseMessage,
                                onGoToDashboardClick = onGoToDashboardClick
                            )
                        }
                }
        }
    }
}
