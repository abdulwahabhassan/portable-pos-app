package com.bankly.banklykozenpos.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.dashboard.navigation.dashBoardNavGraphRoute
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraphRoute

internal fun NavHostController.navigateToAuthenticationNavGraph(
    navOptions: NavOptions? = null
) {
    this.navigate(authenticationNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToDashBoardNavGraph(
    navOptions: NavOptions? = null
) {
    this.navigate(dashBoardNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToPayWithCardNavGraph(
    navOptions: NavOptions? = null
) {
    this.navigate(payWithCardNavGraphRoute, navOptions)
}
