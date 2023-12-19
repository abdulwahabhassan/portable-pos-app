package com.bankly.core.entity

import kotlinx.serialization.Serializable

@Serializable
sealed class Feature(
    val title: String,
    val isQuickAction: Boolean = false,
    val isEnabled: Boolean,
) {
    @Serializable
    data class PayWithCard(val enabled: Boolean = true) :
        Feature(title = "Pay with Card", isQuickAction = true, isEnabled = enabled)

    @Serializable
    data class PayWithTransfer(val enabled: Boolean = true) :
        Feature(title = "Pay with Transfer", isQuickAction = true, isEnabled = enabled)

    @Serializable
    data class CardTransfer(val enabled: Boolean = true) :
        Feature(title = "Card Transfer", isQuickAction = true, isEnabled = enabled)

    @Serializable
    data class SendMoney(val enabled: Boolean = true) :
        Feature(title = "Send Money", isQuickAction = true, isEnabled = enabled)

    @Serializable
    data class PayBills(val enabled: Boolean = true) :
        Feature(title = "Pay Bills", isEnabled = enabled)

    @Serializable
    data class CheckBalance(val enabled: Boolean = true) :
        Feature(title = "Check Balance", isEnabled = enabled)

    @Serializable
    data class PayWithUssd(val enabled: Boolean = true) :
        Feature(title = "Pay with USSD", isEnabled = enabled)

    @Serializable
    data class Float(val enabled: Boolean = true) :
        Feature(title = "Float", isEnabled = enabled)

    @Serializable
    data class EndOfDay(val enabled: Boolean = true) :
        Feature(title = "End of Day (EOD)", isEnabled = enabled)

    @Serializable
    data class NetworkChecker(val enabled: Boolean = true) :
        Feature(title = "Network Checker", isEnabled = enabled)

    @Serializable
    data class Settings(val enabled: Boolean = true) :
        Feature(title = "Settings", isEnabled = enabled)

    companion object {
        fun values(): Array<Feature> {
            return arrayOf(
                PayWithCard(),
                PayWithTransfer(),
                CardTransfer(),
                SendMoney(),
                PayBills(),
                CheckBalance(),
                PayWithUssd(),
                Float(),
                EndOfDay(),
                NetworkChecker(),
                Settings(),
            )
        }
    }
}
