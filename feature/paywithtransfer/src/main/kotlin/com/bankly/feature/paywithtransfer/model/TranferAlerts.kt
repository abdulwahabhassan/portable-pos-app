package com.bankly.feature.paywithtransfer.model

internal data class TransferAlert(
    val title: String,
    val date: String,
    val amount: String,
    val balance: String,
    val isRead: Boolean,
) {
    companion object {
        fun mock(): List<TransferAlert> {
            return listOf(
                TransferAlert(
                    title = "Transfer from Valentine",
                    date = "23, Apr 2022, 12:00:32 PM",
                    amount = "+₦1,053,000",
                    balance = "",
                    isRead = false,
                ),
                TransferAlert(
                    title = "Transfer from Mike",
                    date = "23, Apr 2022, 12:00:32 PM",
                    amount = "+₦210",
                    balance = "",
                    isRead = true,
                ),
                TransferAlert(
                    title = "Transfer from Chisom",
                    date = "23, Apr 2022, 12:00:32 PM",
                    amount = "+₦3,000",
                    balance = "",
                    isRead = true,
                ),
            )
        }
    }
}
