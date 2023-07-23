package com.bankly.feature.dashboard.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

internal fun NavHostController.navigateToHome(
    navOptions: NavOptions? = null
) {
    this.navigate(homeRoute, navOptions)
}

internal fun NavHostController.navigateToTransactions(
    navOptions: NavOptions? = null
) {
    this.navigate(transactionsRoute, navOptions)
}

internal fun NavHostController.navigateToSupport(
    navOptions: NavOptions? = null
) {
    this.navigate(supportRoute, navOptions)
}

internal fun NavHostController.navigateToMore(
    navOptions: NavOptions? = null
) {
    this.navigate(moreRoute, navOptions)
}
