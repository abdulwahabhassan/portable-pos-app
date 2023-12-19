package com.bankly.feature.checkcardbalance.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToCardBalanceRoute(cardBalanceAmount: String, responseCode: String, responseMessage: String) {
    val balanceAmount = Json.encodeToString(cardBalanceAmount)
    val code = Json.encodeToString(responseCode)
    val message = Json.encodeToString(responseMessage)
    val encodedCardBalanceAmount = Uri.encode(balanceAmount)
    val encodedResponseCode = Uri.encode(code)
    val encodedResponseMessage = Uri.encode(message)
    this.navigate("$cardBalanceRoute/$encodedCardBalanceAmount/$encodedResponseCode/$encodedResponseMessage")
}
