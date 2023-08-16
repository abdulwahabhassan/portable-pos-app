package com.bankly.feature.paywithcard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.TransactionType

fun NavGraphBuilder.payWithCardNavGraph(
    onBackPress: () -> Unit,
    appNavController: NavHostController
) {
    navigation(
        route = "$payWithCardNavGraphRoute/{$amountArg}",
        startDestination = payWithCardRoute,
        arguments = listOf(
            navArgument(amountArg) { type = NavType.StringType },
        )
    ) {
        composable(payWithCardRoute) { backStackEntry: NavBackStackEntry ->
            val parentEntry = remember(backStackEntry) {
                appNavController.getBackStackEntry(payWithCardRoute)
            }
            var payWithCardState by rememberPayWithCardState(
                amount = parentEntry.arguments?.getString(
                    amountArg
                )?.toDouble() ?: 0.00,
            )
            PayWithCardNavHost(
                navHostController = payWithCardState.navHostController,
                onBackPress = onBackPress,
                onAccountSelected = { accountType: AccountType ->
                    payWithCardState = payWithCardState.copy(accountType = accountType)
                }
            )
        }
    }
}

@Composable
fun PayWithCardNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onAccountSelected: (AccountType) -> Unit
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = selectAccountTypeRoute,
    ) {
        selectAccountTypeRoute(
            onAccountSelected = { accountType: AccountType ->
                onAccountSelected(accountType)
                navHostController.navigateToInsertCardRoute()
            },
            onBackPress = onBackPress,
        )
        insertCardRoute(
            onCardInserted = {
                navHostController.navigateToEnterPinRoute()
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        enterCardPinRoute(
            onContinueClick = {
                navHostController.navigateToProcessTransactionRoute(
                    TransactionData(
                        TransactionType.BANK_TRANSFER_EXTERNAL,
                        "080999200291",
                        "Hassan Abdulwahab",
                        23000.00,
                        0.00,
                        0.00,
                        "",
                        "",
                        "",
                        AccountNumberType.ACCOUNT_NUMBER,
                        ""
                    )
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCloseClick = onBackPress
        )
        processTransactionRoute(
            onSuccessfulTransaction = {
                navHostController.navigateToTransactionSuccessRoute()
            },
            onFailedTransaction = {
                navHostController.navigateToTransactionFailedRoute()
            }
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = {
                navHostController.navigateToTransactionDetailsRoute()
            },
            onGoHomeClick = onBackPress
        )
        transactionFailedRoute(
            onGoHomeClick = {}
        )
        transactionDetailsRoute(
            onShareClick = { },
            onSmsClick = { },
            onLogComplaintClick = { },
            onGoToHomeClick = onBackPress
        )
    }
}