package com.bankly.banklykozenpos.navigation

import com.bankly.core.designsystem.icon.BanklyIcons

enum class BottomNavDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val destination: String,
) {
    HOME(
        selectedIcon = BanklyIcons.Home_01,
        unselectedIcon = BanklyIcons.Home_02,
        destination = "Dashboard"
    ),
    TRANSACTIONS(
        selectedIcon = BanklyIcons.Receipt_01,
        unselectedIcon = BanklyIcons.Receipt_02,
        destination = "Dashboard"
    ),
    SUPPORT(
        selectedIcon = BanklyIcons.Support_01,
        unselectedIcon = BanklyIcons.Support_02,
        destination = "Dashboard"
    ),
    MORE(
        selectedIcon = BanklyIcons.More_01,
        unselectedIcon = BanklyIcons.More_02,
        destination = "Dashboard"
    )
}