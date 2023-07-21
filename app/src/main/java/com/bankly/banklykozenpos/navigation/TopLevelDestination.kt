package com.bankly.banklykozenpos.navigation

import com.bankly.feature.authentication.navigation.authenticationNavGraph
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph

enum class TopLevelDestination(val route: String) {
    AUTHENTICATION(route = authenticationNavGraph),
    DASHBOARD(route = dashBoardNavGraph)
}