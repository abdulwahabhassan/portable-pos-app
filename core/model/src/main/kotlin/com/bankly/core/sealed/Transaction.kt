package com.bankly.core.sealed

import com.bankly.core.util.Formatter
import kotlinx.serialization.Serializable


@Serializable
sealed class Transaction(
    val acctName: String,
    val acctNumber: String,
    val bank: String,
    val amt: Double,
    val ref: String,
    val msg: String,
) {
    @Serializable
    data class External(
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
    ) : Transaction(
        acctName = accountName,
        acctNumber = accountNumber,
        bank = bankName,
        amt = amount,
        ref = reference,
        msg = message
    )

    @Serializable
    data class Internal(
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
    ) : Transaction(
        acctName = accountName,
        acctNumber = accountNumber,
        bank = bankName,
        amt = amount,
        ref = reference,
        msg = message
    )

    fun toDetailsMap(): Map<String, String> {
        return when(this) {
            is External -> {
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
            is Internal -> {
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
}
