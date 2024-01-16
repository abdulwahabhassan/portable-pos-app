package com.bankly.feature.cardtransfer.navigation

import ProcessPayment
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.core.common.model.AccountType
import com.bankly.core.common.model.TransactionData
import com.bankly.feature.cardtransfer.navigation.viewmodel.CardTransferScreenEvent
import com.bankly.feature.cardtransfer.navigation.viewmodel.CardTransferViewModel
import com.bankly.core.model.sealed.TransactionReceipt
import com.bankly.feature.cardtransfer.navigation.viewmodel.CardTransactionScreenOneShotState
import com.bankly.feature.cardtransfer.util.toTransactionReceipt
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val SUCCESSFUL_STATUS_NAME = "Successful"

fun NavGraphBuilder.cardTransferNavGraph(
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit
) {
    navigation(
        route = cardTransferNavGraphRoute,
        startDestination = cardTransferRoute,
    ) {
        composable(cardTransferRoute) {
            val cardTransferState by rememberCardTransferState()
            CardTransferNavHost(
                navHostController = cardTransferState.navHostController,
                onBackPress = onBackPress,
                onSessionExpired = onSessionExpired,
                onViewTransactionDetailsClick = onViewTransactionDetailsClick
            )
        }
    }
}

@Composable
private fun CardTransferNavHost(
    viewModel: CardTransferViewModel = hiltViewModel(),
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach {
            when (it) {
                is CardTransactionScreenOneShotState.GoToFailedTransactionRoute -> {
                    navHostController.navigateToTransactionFailedRoute(
                        message = it.message,
                        transactionReceipt = it.cardTransferReceipt
                    )
                }

                is CardTransactionScreenOneShotState.ProcessPayment -> {
                    ProcessPayment(context) { transactionResponse, _ ->
                        val cardPaymentReceipt = transactionResponse.toTransactionReceipt()
                        Log.d("onCardPaymentSuccess", "onCardPaymentSuccess -> $cardPaymentReceipt")
                        if (cardPaymentReceipt.statusName.equals(SUCCESSFUL_STATUS_NAME, true)) {
                            navHostController.navigateToProcessTransactionRoute(
                                transactionData = it.transactionData.copy(
                                    responseMessage = transactionResponse.responseMessage ?: "",
                                    responseCode = transactionResponse.responseCode ?: "",
                                ),
                                transactionReceipt = cardPaymentReceipt,
                            )
                        } else {
                            viewModel.sendEvent(
                                CardTransferScreenEvent.OnCardTransferFailed(
                                    transactionData = it.transactionData,
                                    cardPaymentReceipt = cardPaymentReceipt,
                                    cardPaymentReceipt.message
                                )
                            )
                        }
                    }
                }

                is CardTransactionScreenOneShotState.GoToTransactionSuccessfulRoute -> {
                    navHostController.navigateToTransactionSuccessRoute(transactionReceipt = it.cardTransferReceipt)
                }
            }
        }.launchIn(this)
    })


    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = enterRecipientDetailsRoute,
    ) {
        enterRecipientDetailsRoute(
            onBackPress = onBackPress,
            onContinueClick = { transactionData: TransactionData ->
                navHostController.navigateToSelectAccountTypeRoute(transactionData = transactionData)
            },
            onSessionExpired = onSessionExpired,
        )
        selectAccountTypeRoute(
            onAccountSelected = { accountType: AccountType, transactionData: TransactionData ->
                val acctType = when (accountType) {
                    AccountType.SAVINGS -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.SAVINGS
                    AccountType.DEFAULT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.DEFAULT
                    AccountType.CREDIT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CREDIT
                    AccountType.CURRENT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CURRENT
                }
                val transaction = transactionData as TransactionData.CardTransfer
                viewModel.sendEvent(
                    CardTransferScreenEvent.InitCardTransferData(
                        transaction,
                        acctType,
                    )
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCancelPress = onBackPress,
        )
        processTransactionRoute(
            onSuccessfulTransaction = { transactionData, cardPaymentReceipt, cardTransferReceipt ->
                viewModel.sendEvent(
                    CardTransferScreenEvent.OnCardTransferSuccessful(
                        transactionData as TransactionData.CardTransfer,
                        cardPaymentReceipt as TransactionReceipt.CardPayment,
                        cardTransferReceipt as TransactionReceipt.CardTransfer
                    )
                )
            },
            onFailedTransaction = { transactionData, cardPaymentReceipt, message: String ->
                viewModel.sendEvent(
                    CardTransferScreenEvent.OnCardTransferFailed(
                        transactionData as TransactionData.CardTransfer,
                        cardPaymentReceipt as TransactionReceipt.CardPayment,
                        message = message
                    )
                )
            },
            onSessionExpired = onSessionExpired,
        )
        transactionSuccessRoute(
            onViewTransactionDetailsClick = onViewTransactionDetailsClick,
            onGoHomeClick = onBackPress,
        )
        transactionFailedRoute(
            onGoHomeClick = onBackPress,
            onViewTransactionDetailsClick = onViewTransactionDetailsClick,
        )
    }
}
