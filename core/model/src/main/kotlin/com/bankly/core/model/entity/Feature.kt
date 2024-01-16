package com.bankly.core.model.entity

import kotlinx.serialization.Serializable

@Serializable
sealed class Feature(
    val title: String,
    val isQuickAction: Boolean = false,
    val isEnabled: Boolean,
) {
    @Serializable
    data class PayWithCard(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Pay with Card", isQuickAction = true, isEnabled = enabled)

    @Serializable
    data class PayWithTransfer(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Pay with Transfer", isQuickAction = true, isEnabled = enabled)

    @Serializable
    data class CardTransfer(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Card Transfer", isQuickAction = true, isEnabled = enabled)

    @Serializable
    data class SendMoney(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Send Money", isQuickAction = true, isEnabled = enabled)

    @Serializable
    data class PayBills(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Pay Bills", isEnabled = enabled)

    @Serializable
    data class CheckBalance(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Check Balance", isEnabled = enabled)

    @Serializable
    data class PayWithUssd(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Pay with USSD", isEnabled = enabled)

    @Serializable
    data class Float(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Float", isEnabled = enabled)

    @Serializable
    data class EndOfDay(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "End of Day (EOD)", isEnabled = enabled)

    @Serializable
    data class NetworkChecker(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Network Checker", isEnabled = enabled)

    @Serializable
    data class Settings(val enabled: Boolean = true) :
        com.bankly.core.model.entity.Feature(title = "Settings", isEnabled = enabled)

    companion object {
        fun values(): Array<com.bankly.core.model.entity.Feature> {
            return arrayOf(
                com.bankly.core.model.entity.Feature.PayWithCard(),
                com.bankly.core.model.entity.Feature.PayWithTransfer(),
                com.bankly.core.model.entity.Feature.CardTransfer(),
                com.bankly.core.model.entity.Feature.SendMoney(),
                com.bankly.core.model.entity.Feature.PayBills(),
                com.bankly.core.model.entity.Feature.CheckBalance(),
                com.bankly.core.model.entity.Feature.PayWithUssd(),
                com.bankly.core.model.entity.Feature.Float(),
                com.bankly.core.model.entity.Feature.EndOfDay(),
                com.bankly.core.model.entity.Feature.NetworkChecker(),
                com.bankly.core.model.entity.Feature.Settings(),
            )
        }
    }
}
