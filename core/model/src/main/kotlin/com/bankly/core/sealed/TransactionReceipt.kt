package com.bankly.core.sealed

import com.bankly.core.util.Formatter
import kotlinx.serialization.Serializable

@Serializable
sealed class TransactionReceipt(
    val acctName: String,
    val acctNumber: String,
    val bank: String,
    val amt: String,
    val ref: String,
    val msg: String,
) {
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
        acctName = accountName,
        acctNumber = accountNumber,
        bank = bankName,
        amt = amount,
        ref = reference,
        msg = message,
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
        acctName = "",
        acctNumber = "",
        bank = "",
        amt = amount,
        ref = reference,
        msg = message,
    )

    fun toDetailsMap(): Map<String, String> {
        return when (this) {
            is BankTransfer -> {
                mapOf(
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
            }

            is CardPayment -> {
                mapOf(
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
            }
        }
    }

    companion object {
        fun mockBankTransfer(): BankTransfer {
            return BankTransfer(
                accountName = "Hassan Abdulwahab",
                accountNumber = "0428295437",
                bankName = "GTBANK",
                amount = "100.00",
                reference = "177282",
                phoneNumber = "08123939291",
                sourceWallet = 1,
                paymentGateway = 18,
                message = "Transfer Completed Successfully",
                beneficiaryAccount = "0428094437",
                sourceWalletName = "Main",
                dateCreated = "2023-08-15T21:14:40.5225813Z",
                statusName = "", sessionId = "Successful",
            )
        }
    }
}
