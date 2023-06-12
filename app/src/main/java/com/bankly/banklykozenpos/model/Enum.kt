package com.bankly.banklykozenpos.model

import com.bankly.core.designsystem.icon.BanklyIcons

enum class DashboardTab {
    Home,
    POS
}

enum class QuickAction(val title: String, val icon: Int) {
    PayWithCard("Pay with Card", BanklyIcons.PayWithCard),
    PayWithTransfer("Pay with Transfer", BanklyIcons.PayWithTransfer),
    PayWithCash("Pay with Cash", BanklyIcons.PayWithCash),
    SendMoney("Send Money", BanklyIcons.SendMoney)
}