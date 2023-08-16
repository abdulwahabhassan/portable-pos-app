package com.bankly.core.common.model

import com.bankly.core.common.util.Formatter
import com.bankly.core.data.ExternalTransferData
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlinx.serialization.Serializable

@Serializable
data class TransactionData(
    val transactionType: TransactionType,
    val phoneOrAccountNumber: String,
    val accountName: String,
    val amount: Double,
    val vat: Double,
    val fee: Double,
    val bankName: String,
    val bankId: String,
    val narration: String,
    val accountNumberType: AccountNumberType,
    val pin: String,
) {
    fun toDetailsMap(): Map<String, String> {
        return mapOf(
            when (accountNumberType) {
                AccountNumberType.ACCOUNT_NUMBER -> "Account Number"
                AccountNumberType.PHONE_NUMBER -> "Phone Number"
            } to phoneOrAccountNumber,
            "Account Name" to accountName,
            "Amount" to Formatter.formatAmount(
                value = amount.toString(),
                includeNairaSymbol = true
            ),
            "VAT" to Formatter.formatAmount(
                value = vat.toString(),
                includeNairaSymbol = true
            ),
            "Fee" to Formatter.formatAmount(
                value = fee.toString(),
                includeNairaSymbol = true
            )
        )
    }

    fun toExternalTransactionData(): ExternalTransferData {
        return ExternalTransferData(
            accountName = accountName,
            accountNumber = phoneOrAccountNumber,
            bankId = bankId,
            bankName = bankName,
            narration = narration,
            phoneNumber = phoneOrAccountNumber,
            amountToSend = amount.toString(),
            otp = pin,
            deviceId = "",
            clientRequestId = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString()
        )
    }
}