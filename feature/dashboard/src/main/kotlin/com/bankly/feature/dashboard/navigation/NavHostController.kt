package com.bankly.feature.dashboard.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

internal fun NavHostController.navigateToHomeRoute(
    navOptions: NavOptions? = null,
) {

    this.navigate(homeRoute, navOptions)
}

internal fun NavHostController.navigateToTransactionsRoute(
    navOptions: NavOptions? = null,
) {
    this.navigate(transactionsRoute, navOptions)
}

internal fun NavHostController.navigateToSupportRoute(
    navOptions: NavOptions? = null,
) {
    this.navigate(supportRoute, navOptions)
}

internal fun NavHostController.navigateToMoreRoute(
    navOptions: NavOptions? = null,
) {
    this.navigate(moreRoute, navOptions)
}
