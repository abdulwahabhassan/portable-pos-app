package com.bankly.feature.dashboard.model

import com.bankly.core.designsystem.icon.BanklyIcons

enum class DashboardTab {
    Home,
    POS,
}

enum class Feature(val title: String, val icon: Int, val isQuickAction: Boolean = false) {
    PayWithCard("Pay with Card", BanklyIcons.PayWithCard, true),
    PayWithTransfer("Pay with Transfer", BanklyIcons.PayWithTransfer, true),
    CardTransfer("Card Transfer", BanklyIcons.CardTransfer, true),
    SendMoney("Send Money", BanklyIcons.SendMoney, true),
    PayBills("Pay Bills", BanklyIcons.PayBills),
    CheckBalance("Check Balance", BanklyIcons.CheckBalance),
    PayWithUssd("Pay with USSD", BanklyIcons.PayWithUssd),
    Float("Float", BanklyIcons.Float),
    EndOfDay("End of Day (EOD)", BanklyIcons.EndOfDay),
    NetworkChecker("Network Checker", BanklyIcons.NetworkChecker),
    Settings("Settings", BanklyIcons.Settings),
}

