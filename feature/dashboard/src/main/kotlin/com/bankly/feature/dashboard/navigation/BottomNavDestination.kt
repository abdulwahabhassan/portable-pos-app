package com.bankly.feature.dashboard.navigation

import com.bankly.core.designsystem.icon.BanklyIcons


enum class BottomNavDestination(
    val selectedIcon: Int? = null,
    val unselectedIcon: Int? = null,
    val title: String? = null,
    val isBottomNavDestination: Boolean = false,
    val route: String
) {
    HOME(
        selectedIcon = BanklyIcons.Home_01,
        unselectedIcon = BanklyIcons.Home_02,
        title = "Home",
        isBottomNavDestination = true,
        route = homeRoute
    ),
    TRANSACTIONS(
        selectedIcon = BanklyIcons.Receipt_01,
        unselectedIcon = BanklyIcons.Receipt_02,
        title = "Transactions",
        isBottomNavDestination = true,
        route = transactionsRoute
    ),
    SUPPORT(
        selectedIcon = BanklyIcons.Support_01,
        unselectedIcon = BanklyIcons.Support_02,
        title = "Support",
        isBottomNavDestination = true,
        route = supportRoute
    ),
    MORE(
        selectedIcon = BanklyIcons.More_01,
        unselectedIcon = BanklyIcons.More_02,
        title = "More",
        isBottomNavDestination = true,
        route = moreRoute
    ),
}