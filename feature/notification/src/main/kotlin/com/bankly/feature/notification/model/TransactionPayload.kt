package com.bankly.feature.notification.model

import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    var title: String?,

    @SerialName("Body")
    var body: String?,
) : Parcelable
