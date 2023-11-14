package com.bankly.core.sealed

import com.bankly.core.util.Formatter.formatServerDateTime
import kotlinx.serialization.Serializable

@Serializable
sealed class TransactionReceipt (
    val transactionAmount: Double,
    val transactionMessage: String,
){
    @Serializable
    data class BankTransfer(
        val accountName: String,
        val accountNumber: String,
        val bankName: String,
        val amount: Double,
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
    data class CardTransfer(
        val accountNumber: String,
        val bankName: String,
        val amount: Double,
        val reference: String,
        val message: String,
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
        val amount: Double,
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
        val amount: Double,
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
        transactionAmount = amount,
        transactionMessage = description
    )

    @Serializable
    data class TransactionHistory(
    val statusName: String,
    val userType: Long,
    val senderName: String,
    val receiverName: String,
    val balanceBeforeTransaction: Double,
    val id: Long,
    val reference: String,
    val transactionType: Long,
    val transactionTypeName: String,
    val description: String,
    val narration: String,
    val amount: Double,
    val creditAccountNumber: String,
    val parentReference: String,
    val transactionDate: String,
    val credit: Double,
    val debit: Double,
    val balanceAfterTransaction: Double,
    val sender: String,
    val receiver: String,
    val status: Long,
    val charges: Double,
    val aggregatorCommission: Double,
    val hasCharges: Boolean,
    val agentCommission: Double,
    val debitAccountNumber: String,
    val stateId: Long,
    val lgaId: Long,
    val regionId: String,
    val aggregatorId: Long,
    val isCredit: Boolean,
    val isDebit: Boolean
    ) : TransactionReceipt(
        transactionAmount = amount,
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
                "Date/Time" to formatServerDateTime(this.dateCreated),
                "Sender Phone" to this.phoneNumber,
                "Receiver Account" to this.beneficiaryAccount,
                "Receiver Name" to this.accountName,
                "Receiver Bank" to this.bankName,
            )

            is CardTransfer -> mapOf(
                "Transaction Type" to "Card Transfer",
                "Type" to "Debit",
                "Status" to this.statusName,
                "Description" to this.message,
                "Session ID" to this.sessionId,
                "Transaction REF" to this.reference,
                "Date/Time" to formatServerDateTime(this.dateCreated),
                "Receiver Account" to this.accountNumber,
                "Receiver Bank" to this.bankName,
            )

            is CardPayment -> mapOf(
                "Transaction Type" to "Card Payment",
                "Status" to this.statusName,
                "Description" to this.message,
                "Terminal ID" to this.terminalId,
                "Transaction REF" to this.cardType,
                "Date/Time" to formatServerDateTime(this.dateTime),
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
                "Date/Time" to formatServerDateTime(this.dateCreated),
                "Sender Account" to this.receiverName,
                "Sender Name" to this.senderAccountName,
                "Sender Bank" to this.senderBankName,
            )

            is BillPayment -> mapOf(
                "Transaction Type" to "Bill Payment",
                "Bill Type" to this.transactionType,
                "Date/Time" to formatServerDateTime(this.paidOn),
                "Provider" to this.billName,
                "Plan" to this.billItemName,
                getPaidForTitle(this.transactionType) to this.paidFor,
                "Reference" to this.reference,
                "Narration" to this.narration,
                "Token" to this.billToken
            )

            is TransactionHistory -> mapOf(
                "Transaction Type" to this.transactionTypeName,
                "Type" to when (true) {
                    this.isCredit -> "Credit"
                    this.isDebit -> "Debit"
                    else -> ""
                },
                "Date/Time" to formatServerDateTime(this.transactionDate),
                "Status" to this.statusName,
                "Sender Name" to this.senderName,
                "Sender Account" to this.sender,
                "Receiver Name" to this.receiverName,
                "Receiver Account" to this.receiver,
                "Reference" to this.reference,
                "Narration" to this.narration,
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
