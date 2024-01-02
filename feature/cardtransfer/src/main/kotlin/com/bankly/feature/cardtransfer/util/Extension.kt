package com.bankly.feature.cardtransfer.util

import com.bankly.core.sealed.TransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.model.TransactionData
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import java.time.LocalDateTime

internal fun TransactionData.toTransactionReceipt(): TransactionReceipt.CardPayment {
    return TransactionReceipt.CardPayment(
        cardHolderName = this.cardHolderName ?: "",
        cardNumber = this.maskedCardNo ?: "",
        cardType = this.appLabel ?: "",
        amount = this.amount.toDouble(),
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
        dateTime = LocalDateTime.now().toString(),
        rrn = this.rrn ?: "",
        stan = this.stan ?: "",
        terminalId = Tools.terminalId,
        responseCode = this.responseCode ?: "",
    )
}
