package com.bankly.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bankly.core.database.util.Formatter.formatServerDateTimeToLocalDate
import com.bankly.core.model.entity.Transaction
import kotlinx.serialization.Serializable
import java.time.LocalDate

private const val SUCCESSFUL = "Successful"

@Serializable
sealed class EodTransaction {
    @Serializable
    @Entity("eod_bank_transfer")
    data class BankTransfer(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        @ColumnInfo(name = "beneficiary_account_name")
        val beneficiaryAccountName: String,
        @ColumnInfo(name = "beneficiary_bank_name")
        val beneficiaryBankName: String,
        val amount: Double,
        val reference: String,
        @ColumnInfo(name = "phone_number")
        val senderPhoneNumber: String,
        val message: String,
        @ColumnInfo(name = "beneficiary_account")
        val beneficiaryAccountNumber: String,
        @ColumnInfo(name = "date_created")
        val dateCreated: String,
        @ColumnInfo(name = "status_name")
        val statusName: String,
        @ColumnInfo(name = "session_id")
        val sessionId: String,
        @ColumnInfo(name = "is_intra")
        val isIntra: Boolean,
        @ColumnInfo(name = "local_date")
        val localDate: String = formatServerDateTimeToLocalDate(dateCreated),
    ) : EodTransaction()

    @Serializable
    @Entity("eod_card_transfer")
    data class CardTransfer(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        @ColumnInfo(name = "beneficiary_account_number")
        val beneficiaryAccountNumber: String,
        @ColumnInfo(name = "beneficiary_bank_name")
        val beneficiaryBankName: String,
        @ColumnInfo(name = "beneficiary_name")
        val beneficiaryName: String,
        @ColumnInfo(name = "sender_account_number")
        val senderAccountNumber: String,
        @ColumnInfo(name = "sender_bank_name")
        val senderBankName: String,
        @ColumnInfo(name = "sender_name")
        val senderName: String,
        val amount: Double,
        val reference: String,
        val message: String,
        @ColumnInfo(name = "date_created")
        val dateCreated: String,
        @ColumnInfo(name = "status_name")
        val statusName: String,
        @ColumnInfo(name = "session_id")
        val sessionId: String,
        @ColumnInfo(name = "terminal_id")
        val terminalId: String,
        @ColumnInfo(name = "card_type")
        val cardType: String,
        @ColumnInfo(name = "card_number")
        val cardNumber: String,
        @ColumnInfo(name = "response_code")
        val responseCode: String,
        val rrn: String,
        val stan: String,
        @ColumnInfo(name = "local_date")
        val localDate: String = formatServerDateTimeToLocalDate(dateCreated),
    ) : EodTransaction()

    @Serializable
    @Entity("eod_card_payment")
    data class CardPayment(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        @ColumnInfo(name = "card_holder_name")
        val cardHolderName: String,
        @ColumnInfo(name = "card_number")
        val cardNumber: String,
        @ColumnInfo(name = "card_type")
        val cardType: String,
        val amount: Double,
        val reference: String,
        @ColumnInfo(name = "status_name")
        val statusName: String,
        val message: String,
        @ColumnInfo(name = "date_time")
        val dateTime: String,
        val rrn: String,
        val stan: String,
        @ColumnInfo(name = "terminal_id")
        val terminalId: String,
        @ColumnInfo(name = "response_code")
        val responseCode: String,
        @ColumnInfo(name = "local_date")
        val localDate: String = formatServerDateTimeToLocalDate(dateTime),
    ) : EodTransaction()

    @Serializable
    @Entity("eod_pay_with_transfer")
    data class PayWithTransfer(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        @ColumnInfo(name = "sender_account_name")
        val senderAccountName: String,
        @ColumnInfo(name = "sender_account_number")
        val senderAccountNumber: String,
        @ColumnInfo(name = "sender_bank_name")
        val senderBankName: String,
        val amount: Double,
        val reference: String,
        @ColumnInfo(name = "receiver_account_number")
        val receiverAccountNumber: String,
        val message: String,
        @ColumnInfo(name = "receiver_name")
        val receiverName: String,
        @ColumnInfo(name = "receiver_bank_name")
        val receiverBankName: String,
        @ColumnInfo(name = "date_created")
        val dateCreated: String,
        @ColumnInfo(name = "status_name")
        val statusName: String,
        @ColumnInfo(name = "session_id")
        val sessionId: String,
        @ColumnInfo(name = "local_date")
        val localDate: String = formatServerDateTimeToLocalDate(dateCreated),
    ) : EodTransaction()

    @Serializable
    @Entity("eod_bill_payment")
    data class BillPayment(
        @PrimaryKey
        val id: Long,
        val reference: String,
        val narration: String,
        val description: String,
        val amount: Double,
        @ColumnInfo(name = "payment_type")
        val paymentType: String,
        @ColumnInfo(name = "paid_for")
        val paidFor: String,
        @ColumnInfo(name = "paid_for_name")
        val paidForName: String,
        @ColumnInfo(name = "paid_by_account_id")
        val paidByAccountId: Long,
        @ColumnInfo(name = "paid_by_account_no")
        val paidByAccountNo: String,
        @ColumnInfo(name = "paid_by_account_name")
        val paidByAccountName: String,
        @ColumnInfo(name = "paid_on")
        val paidOn: String,
        val polled: Boolean,
        @ColumnInfo(name = "response_status")
        val responseStatus: Long,
        @ColumnInfo(name = "transaction_type")
        val transactionType: String,
        @ColumnInfo(name = "bill_name")
        val billName: String,
        @ColumnInfo(name = "bill_item_name")
        val billItemName: String,
        val receiver: String,
        val commission: Double,
        @ColumnInfo(name = "bill_token")
        val billToken: String,
        @ColumnInfo(name = "is_token_type")
        val isTokenType: Boolean,
        @ColumnInfo(name = "status_name")
        val statusName: String,
        @ColumnInfo(name = "local_date")
        val localDate: String = formatServerDateTimeToLocalDate(paidOn),
    ) : EodTransaction()

    fun toTransaction(): com.bankly.core.model.entity.Transaction.Eod {
        return when (this) {
            is BankTransfer -> com.bankly.core.model.entity.Transaction.Eod.BankTransfer(
                beneficiaryAccountName = beneficiaryAccountName,
                beneficiaryBankName = beneficiaryBankName,
                transAmount = amount,
                ref = reference,
                senderPhoneNumber = senderPhoneNumber,
                message = message,
                beneficiaryAccountNumber = beneficiaryAccountNumber,
                dateCreated = dateCreated,
                statName = statusName,
                sessionId = sessionId,
                transType = 4,
                transTypeName = if (isIntra) "Bankly Transfer" else "Transfer",
                isIntra = isIntra
            )

            is BillPayment -> com.bankly.core.model.entity.Transaction.Eod.BillPayment(
                id = id,
                ref = reference,
                narrationText = narration,
                descriptionText = description,
                statName = statusName,
                paidFor = paidFor,
                paidForName = paidForName,
                paidByAccountId = paidByAccountId,
                paidByAccountNo = paidByAccountNo,
                paidByAccountName = paidByAccountName,
                paidOn = paidOn,
                billName = billName,
                billItemName = billItemName,
                transType = 10,
                transTypeName = transactionType,
                paymentType = paymentType,
                transAmount = amount,
                responseStatus = responseStatus,
                polled = polled,
                receiver = receiver,
                commission = commission,
                billToken = billToken,
                isTokenType = isTokenType
            )

            is CardPayment -> com.bankly.core.model.entity.Transaction.Eod.CardPayment(
                cardHolderName = cardHolderName,
                cardNumber = cardNumber,
                cardType = cardType,
                transAmount = amount,
                ref = reference,
                statName = statusName,
                message = message,
                dateTime = dateTime,
                rrn = rrn,
                stan = stan,
                terminalId = terminalId,
                responseCode = responseCode,
                transType = 102,
                transTypeName = "Pos Cash Withdrawals"
            )

            is CardTransfer -> com.bankly.core.model.entity.Transaction.Eod.CardTransfer(
                beneficiaryAccountNumber = beneficiaryAccountNumber,
                beneficiaryBankName = beneficiaryBankName,
                beneficiaryName = beneficiaryName,
                senderAccountNumber = senderAccountNumber,
                senderBankName = senderBankName,
                senderName = senderName,
                transAmount = amount,
                ref = reference,
                message = message,
                dateCreated = dateCreated,
                statName = statusName,
                sessionId = sessionId,
                transType = 101,
                transTypeName = "POS Transfer",
                terminalId = terminalId,
                cardType = cardType,
                cardNumber = cardNumber,
                responseCode = responseCode,
                rrn = rrn,
                stan = stan
            )

            is PayWithTransfer -> com.bankly.core.model.entity.Transaction.Eod.PayWithTransfer(
                senderAccountName = senderAccountName,
                senderAccountNumber = senderAccountNumber,
                senderBankName = senderBankName,
                transAmount = amount,
                ref = reference,
                receiverAccountNumber = receiverAccountNumber,
                message = message,
                receiverName = receiverName,
                receiverBankName = receiverBankName,
                dateCreated = dateCreated,
                statName = statusName,
                sessionId = sessionId,
                transType = 4,
                transTypeName = "Transfer"
            )
        }
    }
}
