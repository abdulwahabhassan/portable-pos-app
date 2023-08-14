package com.bankly.feature.sendmoney.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.ui.processTransaction.ProcessTransactionRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.common.ui.transactionresponse.TransactionResponseRoute
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.model.ConfirmTransactionDetails
import com.bankly.feature.sendmoney.model.SendMoneyChannel
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryRoute
import com.bankly.feature.sendmoney.ui.confirmtransaction.ConfirmTransactionRoute
import com.bankly.feature.sendmoney.ui.selectchannel.SelectChannelRoute

const val sendMoneyNavGraphRoute = "send_money_graph"
internal const val sendMoneyRoute = sendMoneyNavGraphRoute.plus("/send_money_route")
internal const val selectChannelRoute = sendMoneyRoute.plus("/select_select_channel_screen")
internal const val beneficiaryRoute = sendMoneyRoute.plus("/beneficiary_screen")
internal const val confirmTransactionRoute = sendMoneyRoute.plus("/confirm_transaction_screen")
internal const val processTransactionRoute = sendMoneyRoute.plus("/process_transaction_screen")
internal const val transactionResponseRoute = sendMoneyRoute.plus("/transaction_response_screen")
internal const val transactionDetailsRoute = sendMoneyRoute.plus("/transaction_details_screen")

internal fun NavGraphBuilder.selectChannelRoute(
    onSendMoneyChannelSelected: (SendMoneyChannel) -> Unit,
    onBackPress: () -> Unit
) {
    composable(route = selectChannelRoute) {
        SelectChannelRoute(
            onBackPress = onBackPress,
            onSelectChannel = onSendMoneyChannelSelected,
        )
    }
}

internal fun NavGraphBuilder.beneficiaryRoute(
    onContinueClick: () -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    composable(
        route = "$beneficiaryRoute/{$sendMoneyChannelArg}",
        arguments = listOf(
            navArgument(sendMoneyChannelArg) { type = NavType.StringType },
        )
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
                    destination = channelEnum,
                    onContinueClick = onContinueClick,
                    onCloseClick = onCloseClick
                )
            }
        }
    }
}

internal fun NavGraphBuilder.confirmTransactionRoute(
    onConfirmationSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    composable(route = confirmTransactionRoute) {
        ConfirmTransactionRoute(
            confirmTransactionDetails = ConfirmTransactionDetails(
                "080999200291",
                "Hassan Abdulwahab",
                "#20,0000",
                "#0.00",
                "#0.00",
            ),
            onConfirmationSuccess = onConfirmationSuccess,
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
