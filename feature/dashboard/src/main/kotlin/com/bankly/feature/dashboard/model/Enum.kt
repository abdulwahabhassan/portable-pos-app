package com.bankly.feature.dashboard.model

import com.bankly.core.designsystem.icon.BanklyIcons

enum class DashboardTab {
    Home,
    POS,
}

enum class QuickAction(val title: String, val icon: Int) {
    PayWithCard("Pay with Card", BanklyIcons.PayWithCard),
    PayWithTransfer("Pay with Transfer", BanklyIcons.PayWithTransfer),
    CardTransfer("Card Transfer", BanklyIcons.CardTransfer),
    SendMoney("Send Money", BanklyIcons.SendMoney),
}
