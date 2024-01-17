package com.bankly.feature.notification.model

import android.os.Parcelable
import com.bankly.core.model.entity.RecentFund
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Parcelize
data class TransactionPayload(
    @SerialName("TransactionTypeName")
    var transactionTypeName: String?,

    @SerialName("TransactionReference")
    val transactionReference: String?,

    @SerialName("SenderBankName")
    val senderBankName: String?,

    @SerialName("ReceiverAccountName")
    val receiverAccountName: String?,

    @SerialName("transactionHash")
    val transactionHash: String? = null,

    @SerialName("Amount")
    val amount: Double?,

    @SerialName("ReceiverAccountNumber")
    val receiverAccountNumber: String?,

    @SerialName("PaymentDescription")
    var paymentDescription: String?,

    @SerialName("AccountNumber")
    val accountNumber: String?,

    @SerialName("SenderAccountNumber")
    val senderAccountNumber: String?,

    @SerialName("ReceiverBankName")
    val receiverBankName: String?,

    @SerialName("UserId")
    var userId: String?,

    @SerialName("PhoneNumber")
    var phoneNumber: String?,

    @SerialName("AccountReference")
    val accountReference: String?,

    @SerialName("SenderAccountName")
    val senderAccountName: String?,

    @SerialName("TransactionDate")
    var transactionDate: String?,

    @SerialName("SessionId")
    var sessionID: String?,

    @SerialName("Title")
    var title: String? = null,

    @SerialName("Body")
    var body: String? = null,
) : Parcelable {

    fun toTransactionReceipt(): TransactionReceipt.PayWithTransfer {
        return TransactionReceipt.PayWithTransfer(
            senderAccountName = this.senderAccountName ?: "",
            senderAccountNumber = this.senderAccountNumber ?: "",
            senderBankName = this.senderBankName ?: "",
            amount = this.amount ?: 0.00,
            reference = this.transactionReference ?: "",
            receiverAccountNumber = this.receiverAccountNumber ?: "",
            message = this.paymentDescription ?: this.body ?: "",
            receiverName = this.receiverAccountName ?: "",
            receiverBankName = this.receiverBankName ?: "",
            dateCreated = this.transactionDate ?: "",
            statusName = "Successful",
            sessionId = this.sessionID ?: ""
        )
    }

    fun toRecentFund(): RecentFund {
        return RecentFund(
            transactionReference = this.transactionReference ?: "",
            amount = this.amount ?: 0.00,
            accountReference = this.accountReference ?: "",
            paymentDescription = this.paymentDescription ?: "",
            transactionHash = this.transactionHash ?: "",
            senderAccountNumber = this.senderAccountNumber ?: "",
            senderAccountName = this.senderAccountName ?: "",
            sessionId = this.sessionID ?: "",
            phoneNumber = this.phoneNumber ?: "",
            userId = this.userId ?: "",
            transactionDate = this.transactionDate ?: "",
            seen = false,
            senderBankName = this.senderBankName ?: "",
            receiverBankName = this.receiverBankName ?: "",
            receiverAccountNumber = this.receiverAccountNumber ?: "",
            receiverAccountName = this.receiverAccountName ?: ""
        )
    }

}



