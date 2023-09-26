package com.bankly.feature.paywithcard.util

import com.bankly.core.sealed.TransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.model.TransactionData

fun TransactionData.toTransactionReceipt(): TransactionReceipt.CardPayment {
    return TransactionReceipt.CardPayment(
        cardHolderName = this.cardHolderName ?: "",
        cardNumber = this.maskedCardNo ?: "",
        cardType = this.appLabel ?: "",
        amount = this.amount,
        reference = this.transactionReference ?: "",
        statusName = if (this.responseCode == "00" && responseMessage?.contains(
                "transaction approved",
                true,
            ) == true
        ) {
            "Successful"
        } else {
            "Unsuccessful"
        },
        message = this.responseMessage ?: "",
        dateTime = this.transDate + " " + this.transTime,
        rrn = this.rrn ?: "",
        stan = this.stan ?: "",
        terminalId = "",
        responseCode = this.responseCode ?: "",
    )
}