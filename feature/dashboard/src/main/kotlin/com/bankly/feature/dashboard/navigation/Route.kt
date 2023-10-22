package com.bankly.feature.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.dashboard.model.DashboardTab
import com.bankly.feature.dashboard.model.Feature
import com.bankly.feature.dashboard.model.SupportOption
import com.bankly.feature.dashboard.ui.home.HomeTab
import com.bankly.feature.dashboard.ui.more.MoreRoute
import com.bankly.feature.dashboard.ui.pos.PosTab
import com.bankly.feature.dashboard.ui.support.SupportRoute
import com.bankly.feature.dashboard.ui.transactions.TransactionsRoute

const val dashBoardNavGraphRoute = "dashboard_graph"
internal const val dashBoardRoute = dashBoardNavGraphRoute.plus("/dashboard_route")
internal const val transactionsRoute = dashBoardRoute.plus("/transactions_screen")
internal const val homeRoute = dashBoardRoute.plus("/home_screen")
internal const val supportRoute = dashBoardRoute.plus("/support_screen")
internal const val moreRoute = dashBoardRoute.plus("/more_screen")

internal fun NavGraphBuilder.homeRoute(
    currentHomeTab: DashboardTab,
    onFeatureClick: (Feature) -> Unit,
    onContinueToPayWithCardClick: (Double) -> Unit,
) {
    composable(route = homeRoute) {
        when (currentHomeTab) {
            DashboardTab.POS -> {
                PosTab(
                    onContinueClick = onContinueToPayWithCardClick,
                )
            }

            DashboardTab.Home -> {
                HomeTab(
                    onFeatureCardClick = onFeatureClick,
                )
            }
        }
    }
}

internal fun NavGraphBuilder.transactionsRoute(
    onBackPress: () -> Unit,
    onGoToTransactionDetailsScreen: (TransactionReceipt) -> Unit,
    updateLoadingStatus: (Boolean) -> Unit
) {
    composable(route = transactionsRoute) {
        TransactionsRoute(
            onBackPress = onBackPress,
            onGoToTransactionDetailsScreen = onGoToTransactionDetailsScreen,
            updateLoadingStatus = updateLoadingStatus
        )
    }
}

internal fun NavGraphBuilder.supportRoute(
    onBackPress: () -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit
) {
    composable(route = supportRoute) {
        SupportRoute(
            onBackPress = onBackPress,
            onSupportOptionClick = onSupportOptionClick
        )
    }
}

internal fun NavGraphBuilder.moreRoute(
    onFeatureClick: (Feature) -> Unit,
    onBackPress: () -> Unit
) {
    composable(route = moreRoute) {
        MoreRoute(
            onFeatureCardClick = onFeatureClick,
            onBackPress = onBackPress
        )
    }
}

