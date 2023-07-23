package com.bankly.feature.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.feature.dashboard.model.DashboardTab
import com.bankly.feature.dashboard.ui.home.HomeTab
import com.bankly.feature.dashboard.ui.more.MoreRoute
import com.bankly.feature.dashboard.ui.pos.PosTab
import com.bankly.feature.dashboard.ui.support.SupportRoute
import com.bankly.feature.dashboard.ui.transactions.TransactionsRoute

const val dashBoardNavGraph = "dashboard_graph"
const val dashBoardRoute = dashBoardNavGraph.plus("/dashboard_route")
const val transactionsRoute = dashBoardRoute.plus("/transactions_screen")
const val homeRoute = dashBoardRoute.plus("/home_screen")
const val supportRoute = dashBoardRoute.plus("/support_screen")
const val moreRoute = dashBoardRoute.plus("/more_screen")

internal fun NavGraphBuilder.homeRoute(currentHomeTab: DashboardTab) {
    composable(route = homeRoute) {
        when (currentHomeTab) {
            DashboardTab.POS -> {
                PosTab {}
            }

            DashboardTab.Home -> {
                HomeTab()
            }
        }
    }
}

internal fun NavGraphBuilder.transactionsRoute() {
    composable(route = transactionsRoute) {
        TransactionsRoute()
    }
}

internal fun NavGraphBuilder.supportRoute() {
    composable(route = supportRoute) {
        SupportRoute()
    }
}

internal fun NavGraphBuilder.moreRoute() {
    composable(route = moreRoute) {
        MoreRoute()
    }
}