package com.bankly.core.sealed

import com.bankly.core.util.Formatter
import kotlinx.serialization.Serializable

@Serializable
sealed class TransactionReceipt (
    val transactionAmount: String,
    val transactionMessage: String,
){
    @Serializable
    data class BankTransfer(
        val accountName: String,
        val accountNumber: String,
        val bankName: String,
        val amount: String,
        val reference: String,
        val phoneNumber: String,
        val sourceWallet: Long,
        val paymentGateway: Long,
        val message: String,
        val beneficiaryAccount: String,
        val sourceWalletName: String,
        val dateCreated: String,
        val statusName: String,
        val sessionId: String,
    ) : TransactionReceipt(
        transactionAmount = amount,
        transactionMessage = message
    )

    @Serializable
    data class CardPayment(
        val cardHolderName: String,
        val cardNumber: String,
        val cardType: String,
        val amount: String,
        val reference: String,
        val statusName: String,
        val message: String,
        val dateTime: String,
        val rrn: String,
        val stan: String,
        val terminalId: String,
        val responseCode: String,
    ) : TransactionReceipt(
        transactionAmount = amount,
        transactionMessage = message
    )

    @Serializable
    data class PayWithTransfer(
        val senderAccountName: String,
        val senderAccountNumber: String,
        val senderBankName: String,
        val amount: String,
        val reference: String,
        val receiverAccountNumber: String,
        val message: String,
        val receiverName: String,
        val receiverBankName: String,
        val dateCreated: String,
        val statusName: String,
        val sessionId: String,
    ) : TransactionReceipt(
        transactionAmount = amount,
        transactionMessage = message
    )

    @Serializable
    data class BillPayment(
        val id: Long,
        val reference: String,
        val narration: String,
        val description: String,
        val amount: Double,
        val paymentType: String,
        val paidFor: String,
        val paidForName: String,
        val paidByAccountId: Long,
        val paidByAccountNo: String,
        val paidByAccountName: String,
        val paidOn: String,
        val polled: Boolean,
        val responseStatus: Long,
        val transactionType: String,
        val billName: String,
        val billItemName: String,
        val receiver: String,
        val commission: Double,
        val billToken: String,
        val isTokenType: Boolean
    ) : TransactionReceipt(
        transactionAmount = amount.toString(),
        transactionMessage = description
    )

    fun toDetailsMap(): Map<String, String> {
        return when (this) {
            is BankTransfer -> mapOf(
                "Transaction Type" to "Bank Transfer",
                "Type" to "Debit",
                "Status" to this.statusName,
                "Description" to this.message,
                "Session ID" to this.sessionId,
                "Transaction REF" to this.reference,
                "Date/Time" to Formatter.formatServerDateTime(this.dateCreated),
                "Sender Phone" to this.phoneNumber,
                "Receiver Account" to this.beneficiaryAccount,
                "Receiver Name" to this.accountName,
                "Receiver Bank" to this.bankName,
            )

            is CardPayment -> mapOf(
                "Transaction Type" to "Card Payment",
                "Status" to this.statusName,
                "Description" to this.message,
                "Terminal ID" to this.terminalId,
                "Transaction REF" to this.cardType,
                "Date/Time" to Formatter.formatServerDateTime(this.dateTime),
                "Response Code" to this.responseCode,
                "RRN" to this.rrn,
                "STAN" to this.stan,
            )

            is PayWithTransfer -> mapOf(
                "Transaction Type" to "Transfer Payment",
                "Type" to "Credit",
                "Status" to this.statusName,
                "Description" to this.message,
                "Session ID" to this.sessionId,
                "Transaction REF" to this.reference,
                "Date/Time" to Formatter.formatServerDateTime(this.dateCreated),
                "Sender Account" to this.receiverName,
                "Sender Name" to this.senderAccountName,
                "Sender Bank" to this.senderBankName,
            )

            is BillPayment -> mapOf(
                "Transaction Type" to "Bill Payment",
                "Bill Type" to this.transactionType,
                "Date/Time" to Formatter.formatServerDateTime(this.paidOn),
                "Provider" to this.billName,
                "Plan" to this.billItemName,
                getPaidForTitle(this.transactionType) to this.paidFor,
                "Reference" to this.reference,
                "Narration" to this.narration,
                "Token" to this.billToken
            )
        }
    }

    private fun getPaidForTitle(transactionType: String): String {
        return if (transactionType.contains("Airtime", true) ||
            transactionType.contains("Data", true)) "Phone Number"
        else if (transactionType.contains("Electricity", true)) "Meter Number"
        else if (transactionType.contains("Cable", true)) "IUC/Decoder Number"
        else "UID"
    }
}
