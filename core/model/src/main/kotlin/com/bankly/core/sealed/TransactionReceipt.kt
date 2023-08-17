package com.bankly.core.sealed

import com.bankly.core.util.Formatter
import kotlinx.serialization.Serializable


@Serializable
sealed class TransactionReceipt(
    val acctName: String,
    val acctNumber: String,
    val bank: String,
    val amt: Double,
    val ref: String,
    val msg: String,
) {
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
        val sessionId: String
    ) : TransactionReceipt(
        acctName = accountName,
        acctNumber = accountNumber,
        bank = bankName,
        amt = amount,
        ref = reference,
        msg = message
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
        }
    }

    companion object {
        fun mockBankTransfer(): BankTransfer {
            return BankTransfer(
                accountName = "Hassan Abdulwahab",
                accountNumber = "0428295437",
                bankName = "GTBANK",
                amount = 100.00,
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

