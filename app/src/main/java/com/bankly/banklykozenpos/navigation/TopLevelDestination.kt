package com.bankly.banklykozenpos.navigation

import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph

enum class TopLevelDestination(val route: String) {
    AUTHENTICATION(route = authenticationNavGraphRoute),
    DASHBOARD(route = dashBoardNavGraph)
}