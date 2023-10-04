package com.bankly.feature.paywithtransfer.model

import com.bankly.core.sealed.TransactionReceipt

internal data class TransferAlert(
    val title: String,
    val date: String,
    val amount: String,
    val balance: String,
    val isRead: Boolean,
    val sender: String,
) {
    fun toTransactionReceipt(): TransactionReceipt.PayWithTransfer {
        return  TransactionReceipt.PayWithTransfer(
            sender,
            "",
            "",
            amount,
            "",
            "",
            "",
            "",
            "",
            date,
            "",
            "",
        )
    }

    companion object {
        fun mock(): List<TransferAlert> {
            return listOf(
                TransferAlert(
                    title = "Transfer from Valentine",
                    date = "23, Apr 2022, 12:00:32 PM",
                    amount = "1053000",
                    balance = "Bal ₦2,053,000",
                    isRead = false,
                    sender = "Valentine Ofili"
                ),
                TransferAlert(
                    title = "Transfer from Mike",
                    date = "23, Apr 2022, 12:00:32 PM",
                    amount = "210",
                    balance = "Bal +₦20",
                    isRead = true,
                    sender = "Mike Adenuga"
                ),
                TransferAlert(
                    title = "Transfer from Chisom",
                    date = "23, Apr 2022, 12:00:32 PM",
                    amount = "3000",
                    balance = "Bal +₦2,000",
                    isRead = true,
                    sender = "Chisom Nwafor"
                ),
            )
        }
    }
}
