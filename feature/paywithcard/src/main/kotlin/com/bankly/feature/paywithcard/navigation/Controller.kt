package com.bankly.feature.paywithcard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

internal fun NavHostController.navigateToSelectAccountTypeRoute() {
    this.navigate(selectAccountTypeRoute)
}

internal fun NavHostController.navigateToInsertCardRoute() {
    this.navigate(insertCardRoute)
}

internal fun NavHostController.navigateToEnterPinRoute() {
    this.navigate(enterPinRoute, popUpToStartDestinationInclusiveNavOption(this))
}

internal fun NavHostController.navigateToProcessTransactionRoute() {
    this.navigate(processTransactionRoute, popUpToStartDestinationInclusiveNavOption(this))
}

internal fun NavHostController.navigateToTransactionResponseRoute() {
    this.navigate(transactionResponseRoute, popUpToStartDestinationInclusiveNavOption(this))
}

internal fun NavHostController.navigateToTransactionDetailsRoute() {
    this.navigate(transactionDetailsRoute)
}

internal fun popUpToStartDestinationInclusiveNavOption(navController: NavController): NavOptions {
    return NavOptions.Builder().setPopUpTo(navController.graph.findStartDestination().id, true)
        .build()
}
