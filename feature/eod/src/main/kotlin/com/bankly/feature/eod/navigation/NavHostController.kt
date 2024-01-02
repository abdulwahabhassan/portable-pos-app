package com.bankly.feature.eod.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


internal fun NavHostController.navigateToEodTransactionsRoute() {
    this.navigate(eodTransactionsRoute)
}

internal fun NavHostController.navigateToSyncEodRoute() {
    this.navigate(syncEodRoute)
}

internal fun NavHostController.navigateToExportSuccessfulRoute(title: String, message: String) {
    val encodedTitle = Uri.encode(Json.encodeToString(title))
    val encodedMessage = Uri.encode(Json.encodeToString(message))
    val options = NavOptions.Builder()
        .setPopUpTo(eodRoute, false)
        .build()
    this.navigate("$doneRoute/$encodedTitle/$encodedMessage", options)
}
