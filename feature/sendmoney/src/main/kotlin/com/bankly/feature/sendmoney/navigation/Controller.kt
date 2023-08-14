package com.bankly.feature.sendmoney.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.feature.sendmoney.model.SendMoneyChannel

internal fun NavHostController.navigateToBeneficiaryRoute(channel: SendMoneyChannel) {
    val encodedChannel = Uri.encode(channel.name)
    this.navigate(route = "$beneficiaryRoute/$encodedChannel")
}

internal fun NavHostController.navigateToConfirmTransactionRoute() {
    this.navigate(confirmTransactionRoute)
}

internal fun NavHostController.navigateToProcessTransactionRoute() {
    val options = NavOptions.Builder()
        .setPopUpTo(selectChannelRoute, true)
        .build()
    this.navigate(processTransactionRoute, options)
}

internal fun NavHostController.navigateToTransactionResponseRoute() {
    val options = NavOptions.Builder()
        .setPopUpTo(processTransactionRoute, true)
        .build()
    this.navigate(transactionResponseRoute, options)
}

internal fun NavHostController.navigateToTransactionDetailsRoute() {
    this.navigate(transactionDetailsRoute)
}
