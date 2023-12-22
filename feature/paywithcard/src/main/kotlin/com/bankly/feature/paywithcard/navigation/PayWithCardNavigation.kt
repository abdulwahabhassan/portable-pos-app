package com.bankly.feature.paywithcard.navigation

import ProcessPayment
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bankly.core.common.model.AccountType
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.paywithcard.navigation.viewmodel.PayWithCardScreenEvent
import com.bankly.feature.paywithcard.navigation.viewmodel.PayWithCardScreenOneShotState
import com.bankly.feature.paywithcard.navigation.viewmodel.PayWithCardViewModel
import com.bankly.feature.paywithcard.util.toTransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val SUCCESSFUL_STATUS_NAME = "Successful"

fun NavGraphBuilder.payWithCardNavGraph(
    onBackPress: () -> Unit,
    appNavController: NavHostController,
) {
    navigation(
        route = "$payWithCardNavGraphRoute/{$amountArg}",
        startDestination = payWithCardRoute,
        arguments = listOf(
            navArgument(amountArg) { type = NavType.StringType },
        ),
    ) {
        composable(payWithCardRoute) { backStackEntry: NavBackStackEntry ->
            val parentEntry = remember(backStackEntry) {
                appNavController.getBackStackEntry(payWithCardRoute)
            }
            val amount = parentEntry.arguments?.getString(
                amountArg,
            )?.toDouble() ?: 0.00
            Tools.TransactionAmount = amount
            val payWithCardState by rememberPayWithCardState()
            PayWithCardNavHost(
                navHostController = payWithCardState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun PayWithCardNavHost(
    viewModel: PayWithCardViewModel = hiltViewModel(),
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach {
            when (it) {
                is PayWithCardScreenOneShotState.GoToFailedTransactionRoute -> {
                    navHostController.navigateToTransactionFailedRoute(
                        message = it.message,
                        transactionReceipt = it.cardTransferReceipt
                    )
                }

                is PayWithCardScreenOneShotState.ProcessPayment -> {
                    ProcessPayment(context) { transactionResponse, _ ->
                        val cardPaymentReceipt = transactionResponse.toTransactionReceipt()
                        if (cardPaymentReceipt.statusName.equals(SUCCESSFUL_STATUS_NAME, true)) {
                            viewModel.sendEvent(
                                PayWithCardScreenEvent.OnCardPaymentSuccessful(cardPaymentReceipt)
                            )
                        } else {
                            viewModel.sendEvent(
                                PayWithCardScreenEvent.OnCardPaymentFailed(cardPaymentReceipt)
                            )
                        }
                    }
                }

                is PayWithCardScreenOneShotState.GoToTransactionSuccessfulRoute -> {
                    navHostController.navigateToTransactionSuccessRoute(transactionReceipt = it.cardTransferReceipt)
                }
            }
        }.launchIn(this)
    })

    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = selectAccountTypeRoute,
    ) {
        selectAccountTypeRoute(
            onAccountSelected = { accountType: AccountType ->
                val acctType = when (accountType) {
                    AccountType.SAVINGS -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.SAVINGS
                    AccountType.DEFAULT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.DEFAULT
                    AccountType.CREDIT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CREDIT
                    AccountType.CURRENT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CURRENT
                }
                viewModel.sendEvent(PayWithCardScreenEvent.InitPayWithCard(acctType,))
            },
            onBackPress = onBackPress,
            onCancelPress = onBackPress,
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionDetailsRoute(transactionReceipt = transactionReceipt)
            },
            onGoHomeClick = onBackPress,
        )
        transactionFailedRoute(
            onGoHomeClick = onBackPress,
            onViewTransactionDetailsClick = { transactionReceipt: TransactionReceipt ->
                navHostController.navigateToTransactionDetailsRoute(transactionReceipt = transactionReceipt)
            },
        )
        transactionDetailsRoute(
            onShareClick = { },
            onSmsClick = { },
            onLogComplaintClick = { },
            onGoToHomeClick = onBackPress,
        )
    }
}
