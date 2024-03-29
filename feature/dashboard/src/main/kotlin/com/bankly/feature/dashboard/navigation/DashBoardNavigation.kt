package com.bankly.feature.dashboard.navigation

import android.app.Activity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.core.model.entity.Feature
import com.bankly.core.model.sealed.TransactionReceipt
import com.bankly.feature.dashboard.model.DashboardTab
import com.bankly.feature.dashboard.model.SupportOption
import com.bankly.feature.dashboard.ui.dashboard.DashBoardRoute

fun NavGraphBuilder.dashBoardNavGraph(
    onExitApp: () -> Unit,
    onFeatureClick: (com.bankly.core.model.entity.Feature) -> Unit,
    onContinueToPayWithCardClick: (Double) -> Unit,
    onGoToTransactionDetailsScreen: (TransactionReceipt) -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit,
    onLogOutClick: () -> Unit,
    activity: Activity,
    onSessionExpired: () -> Unit,
) {
    navigation(
        route = dashBoardNavGraphRoute,
        startDestination = dashBoardRoute,
    ) {
        composable(dashBoardRoute) {
            var dashBoardState by rememberDashBoardState()

            //Detect keyboard
            val isKeyboardVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

            DashBoardRoute(
                showLoadingIndicator = dashBoardState.showLoadingIndicator,
                showTopAppBar = dashBoardState.shouldShowTopAppBar,
                currentBottomNavDestination = dashBoardState.currentBottomNavDestination,
                onNavigateToBottomNavDestination = { destination ->
                    dashBoardState.navigateTo(destination)
                },
                showBottomNavBar = if (isKeyboardVisible) false else dashBoardState.shouldShowBottomNavBar,
                content = { padding ->
                    DashBoardNavHost(
                        currentHomeTab = dashBoardState.currentTab,
                        modifier = Modifier.padding(padding),
                        navHostController = dashBoardState.navHostController,
                        onFeatureClick = { feature: com.bankly.core.model.entity.Feature ->
                            if (feature is com.bankly.core.model.entity.Feature.PayWithCard) {
                                dashBoardState = dashBoardState.copy(currentTab = DashboardTab.POS)
                            } else {
                                onFeatureClick(feature)
                            }
                        },
                        onContinueToPayWithCardClick = onContinueToPayWithCardClick,
                        updateLoadingStatus = { isLoading: Boolean ->
                            dashBoardState = dashBoardState.copy(showLoadingIndicator = isLoading)
                        },
                        onGoToTransactionDetailsScreen = onGoToTransactionDetailsScreen,
                        onSupportOptionClick = onSupportOptionClick,
                        onLogOutClick = onLogOutClick,
                        activity = activity,
                        onSessionExpired = onSessionExpired,
                    )
                },
                currentTab = dashBoardState.currentTab,
                onTabChange = { tab: DashboardTab ->
                    dashBoardState = dashBoardState.copy(currentTab = tab)
                },
                onExitApp = onExitApp,
            )
        }
    }
}

@Composable
fun DashBoardNavHost(
    currentHomeTab: DashboardTab,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onFeatureClick: (com.bankly.core.model.entity.Feature) -> Unit,
    onContinueToPayWithCardClick: (Double) -> Unit,
    updateLoadingStatus: (Boolean) -> Unit,
    onGoToTransactionDetailsScreen: (TransactionReceipt) -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit,
    onLogOutClick: () -> Unit,
    activity: Activity,
    onSessionExpired: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = homeRoute,
    ) {
        homeRoute(
            currentHomeTab = currentHomeTab,
            onFeatureClick = onFeatureClick,
            onContinueToPayWithCardClick = onContinueToPayWithCardClick,
            activity = activity,
            onSessionExpired = onSessionExpired,
        )
        transactionsRoute(
            onBackPress = {
                navHostController.popBackStack()
            },
            onGoToTransactionDetailsScreen = { transactionReceipt: TransactionReceipt ->
                onGoToTransactionDetailsScreen(transactionReceipt)
            },
            updateLoadingStatus = updateLoadingStatus,
            onSessionExpired = onSessionExpired,
        )
        supportRoute(
            onBackPress = {
                navHostController.popBackStack()
            },
            onSupportOptionClick = onSupportOptionClick,
        )
        moreRoute(
            onFeatureClick = onFeatureClick,
            onBackPress = {
                navHostController.popBackStack()
            },
            onLogOutClick = onLogOutClick,
        )
    }
}
