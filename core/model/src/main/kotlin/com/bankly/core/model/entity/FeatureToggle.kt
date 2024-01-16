package com.bankly.core.model.entity

import kotlinx.serialization.Serializable

@Serializable
enum class FeatureToggle(val title: String, val isEnabled: Boolean = true) {
    PayWithCard("Pay with Card"),
    PayWithTransfer("Pay with Transfer"),
    CardTransfer("Card Transfer"),
    SendMoney("Send Money"),
    PayBills("Pay Bills"),
    CheckBalance("Check Balance"),
    PayWithUssd("Pay with USSD"),
    Float("Float"),
    EndOfDay("End of Day (EOD)"),
    NetworkChecker("Network Checker"),
    Settings("Settings"),
}
