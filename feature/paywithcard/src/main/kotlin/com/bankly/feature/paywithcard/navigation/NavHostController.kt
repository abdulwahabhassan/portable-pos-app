package com.bankly.feature.paywithcard.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.core.common.model.TransactionData
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToInsertCardRoute() {
    this.navigate(insertCardRoute)
}

internal fun NavHostController.navigateToEnterPinRoute() {
    val options = NavOptions.Builder()
        .setPopUpTo(graph.startDestinationRoute, true)
        .build()
    this.navigate(enterPinRoute, options)
}

internal fun NavHostController.navigateToProcessTransactionRoute(transactionData: TransactionData) {
    val transactionDataString = Json.encodeToString(transactionData)
    val encodedTransactionData = Uri.encode(transactionDataString)
    val options = NavOptions.Builder()
        .setPopUpTo(enterPinRoute, true)
        .build()
    this.navigate("$processTransactionRoute/$encodedTransactionData", options)
}

internal fun NavHostController.navigateToTransactionSuccessRoute(transactionReceipt: TransactionReceipt) {
    val transactionReceiptString = Json.encodeToString(transactionReceipt)
    val encodedTransactionReceipt = Uri.encode(transactionReceiptString)
    val options = NavOptions.Builder()
        .setPopUpTo("$processTransactionRoute/{$transactionDataArg}", true)
        .build()
    this.navigate("$transactionSuccessRoute/$encodedTransactionReceipt", options)
}

internal fun NavHostController.navigateToTransactionFailedRoute(message: String) {
    val encodedMessage = Uri.encode(message)
    val options = NavOptions.Builder()
        .setPopUpTo("$processTransactionRoute/{$transactionDataArg}", true)
        .build()
    this.navigate("$transactionFailedRoute/$encodedMessage", options)
}

internal fun NavHostController.navigateToTransactionDetailsRoute(transactionReceipt: TransactionReceipt) {
    val transactionReceiptString = Json.encodeToString(transactionReceipt)
    val encodedTransactionReceipt = Uri.encode(transactionReceiptString)
    this.navigate("$transactionDetailsRoute/$encodedTransactionReceipt")
}



