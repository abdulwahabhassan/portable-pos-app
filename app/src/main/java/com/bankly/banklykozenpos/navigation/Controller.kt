package com.bankly.banklykozenpos.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.cardtransfer.navigation.cardTransferNavGraphRoute
import com.bankly.feature.dashboard.navigation.dashBoardNavGraphRoute
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraphRoute
import com.bankly.feature.paywithtransfer.navigation.payWithTransferNavGraphRoute
import com.bankly.feature.sendmoney.navigation.sendMoneyNavGraphRoute

internal fun NavHostController.navigateToAuthenticationNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(authenticationNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToDashBoardNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(dashBoardNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToPayWithCardNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(payWithCardNavGraphRoute, navOptions)
}
internal fun NavHostController.navigateToPayWithTransferNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(payWithTransferNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToCardTransferNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(cardTransferNavGraphRoute, navOptions)
}

internal fun NavHostController.navigateToSendMoneyNavGraph(
    navOptions: NavOptions? = null,
) {
    this.navigate(sendMoneyNavGraphRoute, navOptions)
}
