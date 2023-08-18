package com.bankly.feature.sendmoney.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToBeneficiaryRoute(channel: SendMoneyChannel) {
    val encodedChannel = Uri.encode(channel.name)
    this.navigate(route = "$beneficiaryRoute/$encodedChannel")
}

internal fun NavHostController.navigateToConfirmTransactionRoute(transactionData: TransactionData) {
    val transactionDataString = Json.encodeToString(transactionData)
    val encodedTransactionDetails = Uri.encode(transactionDataString)
    this.navigate("$confirmTransactionRoute/$encodedTransactionDetails")
}

internal fun NavHostController.navigateToProcessTransactionRoute(transactionData: TransactionData) {
    val transactionDataString = Json.encodeToString(transactionData)
    val encodedTransactionData = Uri.encode(transactionDataString)
    val options = NavOptions.Builder()
        .setPopUpTo(graph.startDestinationRoute, true)
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