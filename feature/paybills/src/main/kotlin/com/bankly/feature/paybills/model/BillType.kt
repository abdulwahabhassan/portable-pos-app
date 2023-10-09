package com.bankly.feature.paybills.model

import com.bankly.core.designsystem.icon.BanklyIcons

enum class BillType(val title: String, val icon: Int) {
    AIRTIME(title = "Airtime", icon = BanklyIcons.Mobile_Phone),
    INTERNET_DATA(title = "Data", icon = BanklyIcons.Mobile_Phone_Connectivity),
    CABLE_TV(title = "Cable TV", icon = BanklyIcons.Tv_With_Remote_Control),
    ELECTRICITY(title = "Electricity", icon = BanklyIcons.Spark)
}