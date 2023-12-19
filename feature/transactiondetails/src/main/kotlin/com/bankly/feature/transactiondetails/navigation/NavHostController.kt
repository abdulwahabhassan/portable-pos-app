package com.bankly.feature.transactiondetails.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToTransactionDetailsRoute(transactionReceipt: TransactionReceipt) {
    val transactionReceiptString = Json.encodeToString(transactionReceipt)
    val encodedTransactionReceipt = Uri.encode(transactionReceiptString)
    this.navigate("$transactionDetailsRoute/$encodedTransactionReceipt")
}

internal fun NavHostController.navigateToSendReceiptRoute(transactionReceipt: TransactionReceipt) {
    val transactionReceiptString = Json.encodeToString(transactionReceipt)
    val encodedTransactionReceipt = Uri.encode(transactionReceiptString)
    this.navigate("$sendReceiptRoute/$encodedTransactionReceipt")
}

internal fun NavHostController.navigateToDoneRoute(title: String, message: String) {
    val encodedTitle = Uri.encode(Json.encodeToString(title))
    val encodedMessage = Uri.encode(Json.encodeToString(message))
    val options = NavOptions.Builder()
        .setPopUpTo("$transactionDetailsRoute/{$transactionReceiptArg}", false)
        .build()
    this.navigate("$doneRoute/$encodedTitle/$encodedMessage", options)
}
