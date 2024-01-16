package com.bankly.feature.sendmoney.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.ui.confirmtransaction.ConfirmTransactionRoute
import com.bankly.core.common.ui.processtransaction.ProcessTransactionRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.common.ui.transactionfailed.TransactionFailedRoute
import com.bankly.core.common.ui.transactionsuccess.TransactionSuccessRoute
import com.bankly.core.model.sealed.TransactionReceipt
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryRoute
import com.bankly.feature.sendmoney.ui.selectchannel.SelectChannelRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val sendMoneyNavGraphRoute = "send_money_graph"
internal const val sendMoneyRoute = sendMoneyNavGraphRoute.plus("/send_money_route")
internal const val selectChannelRoute = sendMoneyRoute.plus("/select_channel_screen")
internal const val beneficiaryRoute = sendMoneyRoute.plus("/beneficiary_screen")
internal const val confirmTransactionRoute = sendMoneyRoute.plus("/confirm_transaction_screen")
internal const val processTransactionRoute = sendMoneyRoute.plus("/process_transaction_screen")
internal const val transactionSuccessRoute = sendMoneyRoute.plus("/transaction_success_screen")
internal const val transactionFailedRoute = sendMoneyRoute.plus("/transaction_failed_screen")

internal fun NavGraphBuilder.selectChannelRoute(
    onSendMoneyChannelSelected: (SendMoneyChannel) -> Unit,
    onBackPress: () -> Unit,
) {
    composable(route = selectChannelRoute) {
        SelectChannelRoute(
            onBackPress = onBackPress,
            onSelectChannel = onSendMoneyChannelSelected,
        )
    }
}

internal fun NavGraphBuilder.beneficiaryRoute(
    onContinueClick: (TransactionData) -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
    onSessionExpired: () -> Unit,
) {
    composable(
        route = "$beneficiaryRoute/{$sendMoneyChannelArg}",
        arguments = listOf(
            navArgument(sendMoneyChannelArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(sendMoneyChannelArg)?.let { sendMoneyChannelString: String ->
            val channelEnum = when (sendMoneyChannelString) {
                SendMoneyChannel.BANKLY_TO_BANKLY.name -> SendMoneyChannel.BANKLY_TO_BANKLY
                SendMoneyChannel.BANKLY_TO_OTHER.name -> SendMoneyChannel.BANKLY_TO_OTHER
                else -> null
            }
            channelEnum?.let {
                BeneficiaryRoute(
                    onBackPress = onBackPress,
                    sendMoneyChannel = channelEnum,
                    onContinueClick = onContinueClick,
                    onCloseClick = onCloseClick,
                    onSessionExpired = onSessionExpired,
                )
            }
        }
    }
}

internal fun NavGraphBuilder.confirmTransactionRoute(
    onConfirmationSuccess: (TransactionData) -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
    onForgotPinClick: () -> Unit,
) {
    composable(
        route = "$confirmTransactionRoute/{$transactionDataArg}",
        arguments = listOf(
            navArgument(transactionDataArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionDataArg)?.let { transactionDataString: String ->
            val transactionData: TransactionData = Json.decodeFromString(transactionDataString)
            ConfirmTransactionRoute(
                transactionData = transactionData,
                onConfirmationSuccess = onConfirmationSuccess,
                onBackPress = onBackPress,
                onCloseClick = onCloseClick,
                onForgotPinClick = onForgotPinClick,
            )
        }
    }
}

internal fun NavGraphBuilder.processTransactionRoute(
    onSuccessfulTransaction: (TransactionData, TransactionReceipt.CardPayment?, TransactionReceipt) -> Unit,
    onFailedTransaction: (TransactionData, TransactionReceipt.CardPayment?, String) -> Unit,
    onSessionExpired: () -> Unit,
) {
    composable(
        route = "$processTransactionRoute/{$transactionDataArg}",
        arguments = listOf(
            navArgument(transactionDataArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionDataArg)?.let { transactionDataString: String ->
            val transactionData: TransactionData = Json.decodeFromString(transactionDataString)
            ProcessTransactionRoute(
                transactionData = transactionData,
                onTransactionSuccess = onSuccessfulTransaction,
                onFailedTransaction = onFailedTransaction,
                onSessionExpired = onSessionExpired,
            )
        }
    }
}

internal fun NavGraphBuilder.transactionSuccessRoute(
    onGoHomeClick: () -> Unit,
    onViewTransactionDetailsClick: (TransactionReceipt) -> Unit,
) {
    composable(
        route = "$transactionSuccessRoute/{$transactionReceiptArg}",
        arguments = listOf(
            navArgument(transactionReceiptArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionReceiptArg)?.let { transactionReceiptString: String ->
            val transactionReceipt: TransactionReceipt =
                Json.decodeFromString(transactionReceiptString)
            TransactionSuccessRoute(
                transactionReceipt = transactionReceipt,
                message = transactionReceipt.transactionMessage,
                onViewTransactionDetailsClick = onViewTransactionDetailsClick,
                onGoToHome = onGoHomeClick,
            )
        }
    }
}

internal fun NavGraphBuilder.transactionFailedRoute(
    onGoHomeClick: () -> Unit,
) {
    composable(
        route = "$transactionFailedRoute/{$messageArg}",
        arguments = listOf(
            navArgument(messageArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(messageArg)?.let { messageString: String ->
            val message: String = Json.decodeFromString(messageString)
            TransactionFailedRoute(
                onGoToHome = onGoHomeClick,
                message = message,
            )
        }
    }
}

