package com.bankly.core.common.model

enum class SendMoneyChannel(
    val title: String,
    val icon: Int,
    val description: String,
    val screenTitle: String,
) {
    BANKLY_TO_BANKLY(
        title = "Bankly to Bankly Transfer",
        icon = com.bankly.core.designsystem.R.drawable.ic_send_money_bankly,
        description = "Send money to a Bankly account",
        screenTitle = "Bankly Account",

    ),
    BANKLY_TO_OTHER(
        title = "Bankly to Other Bank Transfer",
        icon = com.bankly.core.designsystem.R.drawable.ic_sendmoney_other_bank,
        description = "Send money to commercial and microfinance banks in Nigeria.",
        screenTitle = "Other Banks",
    ),
}
