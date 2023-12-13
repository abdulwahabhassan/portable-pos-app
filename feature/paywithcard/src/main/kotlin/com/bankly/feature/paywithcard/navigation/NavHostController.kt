package com.bankly.feature.paywithcard.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.core.common.model.TransactionData
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToTransactionSuccessRoute(transactionReceipt: TransactionReceipt) {
    val transactionReceiptString = Json.encodeToString(transactionReceipt)
    val encodedTransactionReceipt = Uri.encode(transactionReceiptString)
    val options = NavOptions.Builder()
        .setPopUpTo(graph.startDestinationRoute, true)
        .build()
    this.navigate("$transactionSuccessRoute/$encodedTransactionReceipt", options)
}

internal fun NavHostController.navigateToTransactionFailedRoute(message: String, transactionReceipt: TransactionReceipt?) {
    val messageString = Json.encodeToString(message)
    val encodedMessage = Uri.encode(messageString)
    val transactionReceiptString = Json.encodeToString(transactionReceipt)
    val encodedTransactionReceipt = Uri.encode(transactionReceiptString)
    val options = NavOptions.Builder()
        .setPopUpTo(graph.startDestinationRoute, true)
        .build()
    this.navigate("$transactionFailedRoute/$encodedMessage/$encodedTransactionReceipt", options)
}

internal fun NavHostController.navigateToTransactionDetailsRoute(transactionReceipt: TransactionReceipt) {
    val transactionReceiptString = Json.encodeToString(transactionReceipt)
    val encodedTransactionReceipt = Uri.encode(transactionReceiptString)
    this.navigate("$transactionDetailsRoute/$encodedTransactionReceipt")
}
