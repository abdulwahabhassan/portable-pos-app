package com.bankly.feature.paybills.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.ui.processtransaction.ProcessTransactionRoute
import com.bankly.core.common.ui.transactiondetails.TransactionDetailsRoute
import com.bankly.core.common.ui.transactionfailed.TransactionFailedRoute
import com.bankly.core.common.ui.transactionsuccess.TransactionSuccessRoute
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.core.common.ui.confirmtransaction.ConfirmTransactionRoute
import com.bankly.feature.paybills.model.BillType
import com.bankly.feature.paybills.ui.beneficiary.BeneficiaryRoute
import com.bankly.feature.paybills.ui.selectbilltype.SelectBillTypeRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val billPaymentNavGraphRoute = "bill_payment_graph"
internal const val billPaymentRoute = billPaymentNavGraphRoute.plus("/bill_payment_route")
internal const val selectBillTypeRoute = billPaymentRoute.plus("/select_bill_type_screen")
internal const val beneficiaryRoute = billPaymentRoute.plus("/beneficiary_screen")
internal const val confirmTransactionRoute = billPaymentRoute.plus("/confirm_transaction_screen")
internal const val processTransactionRoute = billPaymentRoute.plus("/process_transaction_screen")
internal const val transactionSuccessRoute = billPaymentRoute.plus("/transaction_success_screen")
internal const val transactionFailedRoute = billPaymentRoute.plus("/transaction_failed_screen")
internal const val transactionDetailsRoute = billPaymentRoute.plus("/transaction_details_screen")

internal fun NavGraphBuilder.selectBillTypeRoute(
    onBillTypeSelected: (BillType) -> Unit,
    onBackPress: () -> Unit,
) {
    composable(route = selectBillTypeRoute) {
        SelectBillTypeRoute(
            onBackPress = onBackPress,
            onBillTypeSelected = onBillTypeSelected,
        )
    }
}

internal fun NavGraphBuilder.beneficiaryRoute(
    onContinueClick: (TransactionData) -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
    onSessionExpired: () -> Unit

) {
    composable(
        route = "$beneficiaryRoute/{$billTypeArg}",
        arguments = listOf(
            navArgument(billTypeArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(billTypeArg)?.let { billTypeString: String ->
            val billTypeEnum = when (billTypeString) {
                BillType.AIRTIME.name -> BillType.AIRTIME
                BillType.INTERNET_DATA.name -> BillType.INTERNET_DATA
                BillType.ELECTRICITY.name -> BillType.ELECTRICITY
                BillType.CABLE_TV.name -> BillType.CABLE_TV
                else -> null
            }
            billTypeEnum?.let {
                BeneficiaryRoute(
                    onBackPress = onBackPress,
                    billType = billTypeEnum,
                    onContinueClick = onContinueClick,
                    onCloseClick = onCloseClick,
                    onSessionExpired = onSessionExpired
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
    onSuccessfulTransaction: (TransactionReceipt) -> Unit,
    onFailedTransaction: (String) -> Unit,
    onSessionExpired: () -> Unit
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
                onFailedTransaction = { message: String, _ ->
                    onFailedTransaction(message)
                },
                onSessionExpired = onSessionExpired
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
        it.arguments?.getString(transactionReceiptArg)?.let { transactionReceiptString: String ->
            val transactionReceipt: TransactionReceipt =
                Json.decodeFromString(transactionReceiptString)
            TransactionDetailsRoute(
                transactionReceipt = transactionReceipt,
                isSuccess = transactionReceipt.isSuccessfulTransaction(),
                onShareClick = onShareClick,
                onSmsClick = onSmsClick,
                onLogComplaintClick = onLogComplaintClick,
                onGoToHomeClick = onGoToHomeClick,
            )
        }
    }
}
