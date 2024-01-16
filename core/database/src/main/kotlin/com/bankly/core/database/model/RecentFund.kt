package com.bankly.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_fund")
data class RecentFund(
    @PrimaryKey
    @ColumnInfo(name = "transaction_reference")
    val transactionReference: String,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "account_reference")
    val accountReference: String,
    @ColumnInfo(name = "payment_description")
    val paymentDescription: String,
    @ColumnInfo(name = "sender_account_number")
    val senderAccountNumber: String,
    @ColumnInfo(name = "sender_account_name")
    val senderAccountName: String,
    @ColumnInfo(name = "session_id")
    val sessionId: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "transaction_date")
    val transactionDate: String,
    @ColumnInfo(name = "seen")
    val seen: Boolean = false,
    @ColumnInfo(name = "senderBankName")
    val senderBankName: String,
    @ColumnInfo(name = "receiverBankName")
    val receiverBankName: String,
    @ColumnInfo(name = "receiverAccountNumber")
    val receiverAccountNumber: String,
    @ColumnInfo(name = "receiverAccountName")
    val receiverAccountName: String

) {
    fun mapToRecentFund(): RecentFund {
        return RecentFund(
            transactionReference = this.transactionReference,
            amount = this.amount,
            accountReference = this.accountReference,
            paymentDescription = this.paymentDescription,
            senderAccountNumber = this.senderAccountNumber,
            senderAccountName = this.senderAccountName,
            sessionId = this.sessionId,
            phoneNumber = this.phoneNumber,
            userId = this.userId,
            transactionDate = this.transactionDate,
            seen = this.seen,
            senderBankName = this.senderBankName,
            receiverBankName = this.receiverBankName,
            receiverAccountNumber = this.receiverAccountNumber,
            receiverAccountName = this.receiverAccountName
        )
    }
}
