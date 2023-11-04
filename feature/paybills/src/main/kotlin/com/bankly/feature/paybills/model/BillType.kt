package com.bankly.feature.paybills.model

import com.bankly.core.designsystem.icon.BanklyIcons

enum class BillType(val title: String, val icon: Int) {
    AIRTIME(title = "Airtime", icon = BanklyIcons.MobilePhone),
    INTERNET_DATA(title = "Data", icon = BanklyIcons.MobilePhoneConnectivity),
    CABLE_TV(title = "Cable TV", icon = BanklyIcons.TvWithRemoteControl),
    ELECTRICITY(title = "Electricity", icon = BanklyIcons.Spark)
}