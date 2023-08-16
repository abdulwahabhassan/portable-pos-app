package com.bankly.feature.cardtransfer.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.core.common.model.TransactionData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavController.navigateToEnterRecipientDetailsRoute() {
    this.navigate(enterRecipientDetailsRoute)
}

internal fun NavHostController.navigateToSelectAccountTypeRoute() {
    this.navigate(selectAccountTypeRoute)
}

internal fun NavHostController.navigateToInsertCardRoute() {
    this.navigate(insertCardRoute)
}

internal fun NavHostController.navigateToEnterPinRoute() {
    this.navigate(enterPinRoute, popUpToStartDestinationInclusiveNavOption(this))
}

internal fun NavHostController.navigateToProcessTransactionRoute(transactionData: TransactionData) {
    val transactionDetailsAsString = Json.encodeToString(transactionData)
    val encodedTransactionDetails = Uri.encode(transactionDetailsAsString)
    this.navigate("$processTransactionRoute/$encodedTransactionDetails", popUpToStartDestinationInclusiveNavOption(this))
}

internal fun NavHostController.navigateToTransactionSuccessRoute() {
    this.navigate(transactionSuccessRoute, popUpToStartDestinationInclusiveNavOption(this))
}

internal fun NavHostController.navigateToTransactionFailedRoute() {
    this.navigate(transactionFailedRoute, popUpToStartDestinationInclusiveNavOption(this))
}

internal fun NavHostController.navigateToTransactionDetailsRoute() {
    this.navigate(transactionDetailsRoute)
}

internal fun popUpToStartDestinationInclusiveNavOption(navController: NavController): NavOptions {
    return NavOptions.Builder().setPopUpTo(navController.graph.findStartDestination().id, true)
        .build()
}
