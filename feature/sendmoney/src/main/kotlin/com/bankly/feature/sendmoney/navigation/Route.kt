package com.bankly.feature.sendmoney.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.ui.processtransaction.ProcessTransactionRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.common.ui.transactionfailed.TransactionFailedRoute
import com.bankly.core.common.ui.transactionsuccess.TransactionSuccessRoute
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryRoute
import com.bankly.core.common.ui.confirmtransaction.ConfirmTransactionRoute
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
internal const val transactionDetailsRoute = sendMoneyRoute.plus("/transaction_details_screen")

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
) {
    composable(
        route = "$beneficiaryRoute/{$sendMoneyChannelArg}",
        arguments = listOf(
            navArgument(sendMoneyChannelArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(sendMoneyChannelArg)?.let { sendMoneyChannel: String ->
            val channelEnum = when (sendMoneyChannel) {
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
        it.arguments?.getString(transactionDataArg)?.let { transactionData: String ->
            val data: TransactionData = Json.decodeFromString(transactionData)
            ConfirmTransactionRoute(
                transactionData = data,
                onConfirmationSuccess = onConfirmationSuccess,
                onBackPress = onBackPress,
                onCloseClick = onCloseClick,
                onForgotPinClick = onForgotPinClick,
            )
        }
    }
}

internal fun NavGraphBuilder.processTransactionRoute(
    onSuccessfulTransaction: (TransactionReceipt) -> Unit,
    onFailedTransaction: (String) -> Unit,
) {
    composable(
        route = "$processTransactionRoute/{$transactionDataArg}",
        arguments = listOf(
            navArgument(transactionDataArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionDataArg)?.let { transactionData: String ->
            val data: TransactionData = Json.decodeFromString(transactionData)
            ProcessTransactionRoute(
                transactionData = data,
                onTransactionSuccess = onSuccessfulTransaction,
                onFailedTransaction = onFailedTransaction,
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
        it.arguments?.getString(transactionReceiptArg)?.let { transaction: String ->
            val trans: TransactionReceipt = Json.decodeFromString(transaction)
            TransactionSuccessRoute(
                transactionReceipt = trans,
                message = trans.transactionMessage,
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
        it.arguments?.getString(messageArg)?.let { message: String ->
            TransactionFailedRoute(
                onGoToHome = onGoHomeClick,
                message = message,
            )
        }
    }
}

internal fun NavGraphBuilder.transactionDetailsRoute(
    onShareClick: () -> Unit,
    onSmsClick: (TransactionReceipt) -> Unit,
    onLogComplaintClick: () -> Unit,
    onGoToHomeClick: () -> Unit,
) {
    composable(
        route = "$transactionDetailsRoute/{$transactionReceiptArg}",
        arguments = listOf(
            navArgument(transactionReceiptArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(transactionReceiptArg)?.let { transaction: String ->
            val trans: TransactionReceipt = Json.decodeFromString(transaction)
            TransactionDetailsRoute(
                transactionReceipt = trans,
                onShareClick = onShareClick,
                onSmsClick = onSmsClick,
                onLogComplaintClick = onLogComplaintClick,
                onGoToHomeClick = onGoToHomeClick,
            )
        }
    }
}
