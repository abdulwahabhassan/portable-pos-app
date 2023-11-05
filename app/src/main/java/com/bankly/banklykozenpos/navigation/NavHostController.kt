package com.bankly.banklykozenpos.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.cardtransfer.navigation.cardTransferNavGraphRoute
import com.bankly.feature.contactus.navigation.contactUsNavGraphRoute
import com.bankly.feature.dashboard.navigation.dashBoardNavGraphRoute
import com.bankly.feature.eod.navigation.eodNavGraphRoute
import com.bankly.feature.logcomplaints.navigation.logComplaintNavGraphRoute
import com.bankly.feature.paybills.navigation.billPaymentNavGraphRoute
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraphRoute
import com.bankly.feature.paywithtransfer.navigation.payWithTransferNavGraphRoute
import com.bankly.feature.sendmoney.navigation.sendMoneyNavGraphRoute
import com.bankly.feature.transactiondetails.navigation.transactionDetailsNavGraphRoute
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToAuthenticationNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(authenticationNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToDashBoardNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(dashBoardNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToPayWithCardNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(payWithCardNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToPayWithCardNavGraph(
    amount: Double,
    navOptions: NavOptions? = null,
) {
    val encodedAmount = Uri.encode(amount.toString())
    this.navigate(
        route = "$payWithCardNavGraphRoute/$encodedAmount",
        navOptions = navOptions,
    )
}

internal fun NavHostController.navigateToPayWithTransferNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(payWithTransferNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToCardTransferNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(cardTransferNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToSendMoneyNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(sendMoneyNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToPayBillsNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(billPaymentNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToEodNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(eodNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToTransactionDetailsNavGraph(
    transactionReceipt: TransactionReceipt,
    navOptions: NavOptions? = null,
) {
    val transactionReceiptString = Json.encodeToString(transactionReceipt)
    val encodedTransactionReceipt = Uri.encode(transactionReceiptString)
    this.navigate("$transactionDetailsNavGraphRoute/$encodedTransactionReceipt", navOptions)
}

internal fun NavHostController.navigateToContactUsNavGraph(navOptions: NavOptions? = null,) {
    this.navigate(contactUsNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToLogComplaintNavGraph(navOptions: NavOptions? = null,) {
    this.navigate(logComplaintNavGraphRoute, navOptions)
}
