package com.bankly.core.model.entity

import com.bankly.core.model.sealed.TransactionReceipt
import java.time.LocalDateTime

sealed class Transaction(
    val isCreditTransaction: Boolean,
    val isDebitTransaction: Boolean,
    val transactionTypeLabel: String,
    val date: String,
    val transactionAmount: Double,
    val transactionReference: String,
    val transactionTypeId: Long,
) {
    data class History(
        val creditAccountNo: String,
        val debitAccountNo: String,
        val transactionBy: String,
        val channelName: String,
        val statusName: String,
        val userType: Long,
        val userId: Long,
        val initiator: String,
        val archived: Boolean,
        val product: String,
        val hasProduct: Boolean,
        val senderName: String,
        val receiverName: String,
        val balanceBeforeTransaction: Double,
        val leg: Long,
        val id: Long,
        val reference: String,
        val transactionType: Long,
        val transactionTypeName: String,
        val description: String,
        val narration: String,
        val amount: Double,
        val creditAccountNumber: String,
        val deditAccountNumber: String,
        val parentReference: String,
        val transactionDate: String,
        val credit: Double,
        val debit: Double,
        val balanceAfterTransaction: Double,
        val sender: String,
        val receiver: String,
        val channel: Long,
        val status: Long,
        val charges: Double,
        val aggregatorCommission: Double,
        val hasCharges: Boolean,
        val agentCommission: Double,
        val debitAccountNumber: String,
        val initiatedBy: String,
        val stateId: Long,
        val lgaId: Long,
        val regionId: String,
        val aggregatorId: Long,
        val isCredit: Boolean,
        val isDebit: Boolean,
    ) : com.bankly.core.model.entity.Transaction(
        isCreditTransaction = isCredit,
        transactionTypeLabel = transactionTypeName,
        date = transactionDate,
        transactionAmount = amount,
        isDebitTransaction = isDebit,
        transactionReference = reference,
        transactionTypeId = transactionType
    )

    sealed class Eod(
        val statusName: String,
        val reference: String,
        val transactionType: Long,
        val transactionTypeName: String,
        val description: String,
        val narration: String,
        val amount: Double,
        val transactionDate: String,
        val isCredit: Boolean,
        val isDebit: Boolean,
    ) : com.bankly.core.model.entity.Transaction(
        isCreditTransaction = isCredit,
        transactionTypeLabel = transactionTypeName,
        date = transactionDate,
        transactionAmount = amount,
        isDebitTransaction = isDebit,
        transactionReference = reference,
        transactionTypeId = transactionType
    ) {

        data class BankTransfer(
            val beneficiaryAccountName: String,
            val beneficiaryBankName: String,
            val transAmount: Double,
            val ref: String,
            val senderPhoneNumber: String,
            val message: String,
            val beneficiaryAccountNumber: String,
            val dateCreated: String,
            val statName: String,
            val sessionId: String,
            val transType: Long,
            val transTypeName: String,
            val isIntra: Boolean,
        ) : com.bankly.core.model.entity.Transaction.Eod(
            statusName = statName,
            reference = ref,
            transactionType = transType,
            transactionTypeName = transTypeName,
            description = message,
            narration = "",
            amount = transAmount,
            transactionDate = dateCreated,
            isCredit = false,
            isDebit = true
        )

        data class CardTransfer(
            val beneficiaryAccountNumber: String,
            val beneficiaryBankName: String,
            val beneficiaryName: String,
            val senderAccountNumber: String,
            val senderBankName: String,
            val senderName: String,
            val transAmount: Double,
            val ref: String,
            val message: String,
            val dateCreated: String,
            val statName: String,
            val sessionId: String,
            val transType: Long,
            val transTypeName: String,
            val terminalId: String,
            val cardType: String,
            val cardNumber: String,
            val responseCode: String,
            val rrn: String,
            val stan: String,
        ) : com.bankly.core.model.entity.Transaction.Eod(
            statusName = statName,
            reference = ref,
            transactionType = transType,
            transactionTypeName = transTypeName,
            description = message,
            narration = "",
            amount = transAmount,
            transactionDate = dateCreated,
            isCredit = false,
            isDebit = true
        )

        data class CardPayment(
            val cardHolderName: String,
            val cardNumber: String,
            val cardType: String,
            val transAmount: Double,
            val ref: String,
            val statName: String,
            val message: String,
            val dateTime: String,
            val rrn: String,
            val stan: String,
            val terminalId: String,
            val responseCode: String,
            val transType: Long,
            val transTypeName: String,
        ) : com.bankly.core.model.entity.Transaction.Eod(
            statusName = statName,
            reference = ref,
            transactionType = transType,
            transactionTypeName = transTypeName,
            description = message,
            narration = "",
            amount = transAmount,
            transactionDate = dateTime,
            isCredit = false,
            isDebit = true
        )

        data class PayWithTransfer(
            val senderAccountName: String,
            val senderAccountNumber: String,
            val senderBankName: String,
            val transAmount: Double,
            val ref: String,
            val receiverAccountNumber: String,
            val message: String,
            val receiverName: String,
            val receiverBankName: String,
            val dateCreated: String,
            val statName: String,
            val sessionId: String,
            val transType: Long,
            val transTypeName: String,
        ) : com.bankly.core.model.entity.Transaction.Eod(
            statusName = statName,
            reference = ref,
            transactionType = transType,
            transactionTypeName = transTypeName,
            description = message,
            narration = "",
            amount = transAmount,
            transactionDate = dateCreated,
            isCredit = true,
            isDebit = false
        )

        data class BillPayment(
            val id: Long,
            val ref: String,
            val narrationText: String,
            val descriptionText: String,
            val statName: String,
            val paidFor: String,
            val paidForName: String,
            val paidByAccountId: Long,
            val paidByAccountNo: String,
            val paidByAccountName: String,
            val paidOn: String,
            val billName: String,
            val billItemName: String,
            val transType: Long,
            val transTypeName: String,
            val paymentType: String,
            val transAmount: Double,
            val responseStatus: Long,
            val polled: Boolean,
            val receiver: String,
            val commission: Double,
            val billToken: String,
            val isTokenType: Boolean,
        ) : com.bankly.core.model.entity.Transaction.Eod(
            statusName = statName,
            reference = ref,
            transactionType = transType,
            transactionTypeName = transTypeName,
            description = descriptionText,
            narration = narrationText,
            amount = transAmount,
            transactionDate = paidOn,
            isCredit = false,
            isDebit = true
        )

    }

    fun toTransactionReceipt(): TransactionReceipt {
        return when (this) {
            is com.bankly.core.model.entity.Transaction.History -> TransactionReceipt.History(
                statusName = statusName,
                userType = userType,
                senderName = senderName,
                receiverName = receiverName,
                balanceBeforeTransaction = balanceBeforeTransaction,
                id = id,
                reference = reference,
                transactionType = transactionType,
                transactionTypeName = transactionTypeName,
                description = description,
                narration = narration,
                amount = amount,
                creditAccountNumber = creditAccountNumber,
                parentReference = parentReference,
                transactionDate = transactionDate,
                credit = credit,
                debit = debit,
                balanceAfterTransaction = balanceAfterTransaction,
                sender = sender,
                receiver = receiver,
                status = status,
                charges = charges,
                aggregatorCommission = aggregatorCommission,
                hasCharges = hasCharges,
                agentCommission = agentCommission,
                debitAccountNumber = debitAccountNumber,
                stateId = stateId,
                lgaId = lgaId,
                regionId = regionId,
                aggregatorId = aggregatorId,
                isCredit = isCredit,
                isDebit = isDebit,
            )

            is com.bankly.core.model.entity.Transaction.Eod -> when (this) {
                is com.bankly.core.model.entity.Transaction.Eod.BankTransfer -> TransactionReceipt.BankTransfer(
                    accountName = beneficiaryAccountName,
                    accountNumber = beneficiaryAccountNumber,
                    bankName = beneficiaryBankName,
                    amount = transAmount,
                    reference = ref,
                    phoneNumber = senderPhoneNumber,
                    sourceWallet = 0L,
                    paymentGateway = 0L,
                    message = message,
                    beneficiaryAccount = beneficiaryAccountNumber,
                    sourceWalletName = "",
                    dateCreated = dateCreated,
                    statusName = statName,
                    sessionId = sessionId,
                    isIntra = isIntra
                )

                is com.bankly.core.model.entity.Transaction.Eod.BillPayment -> TransactionReceipt.BillPayment(
                    id = id,
                    reference = ref,
                    narration = narrationText,
                    description = descriptionText,
                    amount = transAmount,
                    paymentType = paymentType,
                    paidFor = paidFor,
                    paidForName = paidForName,
                    paidByAccountId = paidByAccountId,
                    paidByAccountNo = paidByAccountNo,
                    paidByAccountName = paidByAccountName,
                    paidOn = paidOn,
                    polled = polled,
                    responseStatus = responseStatus,
                    transactionType = transTypeName,
                    billName = billName,
                    billItemName = billItemName,
                    receiver = receiver,
                    commission = commission,
                    billToken = billToken,
                    isTokenType = isTokenType,
                )

                is com.bankly.core.model.entity.Transaction.Eod.CardPayment -> TransactionReceipt.CardPayment(
                    cardHolderName = cardHolderName,
                    cardNumber = cardNumber,
                    cardType = cardType,
                    amount = amount,
                    reference = transactionReference,
                    statusName = if (responseCode == "00" && message.contains(
                            "transaction approved",
                            true,
                        )
                    ) {
                        "Successful"
                    } else {
                        "Unsuccessful"
                    },
                    message = message,
                    dateTime = LocalDateTime.now().toString(),
                    rrn = rrn,
                    stan = stan,
                    terminalId = "",
                    responseCode = responseCode,
                )

                is com.bankly.core.model.entity.Transaction.Eod.CardTransfer -> TransactionReceipt.CardTransfer(
                    beneficiaryAccountNumber = beneficiaryAccountNumber,
                    beneficiaryBankName = beneficiaryBankName,
                    amount = amount,
                    reference = reference,
                    message = message,
                    dateCreated = dateCreated,
                    statusName = statusName,
                    sessionId = sessionId,
                    terminalId = terminalId,
                    cardType = cardType,
                    cardNumber = cardNumber,
                    beneficiaryName = beneficiaryName,
                    responseCode = responseCode,
                    rrn = rrn,
                    stan = stan
                )

                is com.bankly.core.model.entity.Transaction.Eod.PayWithTransfer -> TransactionReceipt.PayWithTransfer(
                    senderAccountName = senderAccountName,
                    senderAccountNumber = senderAccountNumber,
                    senderBankName = senderBankName,
                    amount = amount,
                    reference = transactionReference,
                    receiverAccountNumber = receiverAccountNumber,
                    message = message,
                    receiverName = receiverName,
                    receiverBankName = receiverBankName,
                    dateCreated = transactionDate,
                    statusName = "Successful",
                    sessionId = sessionId,
                )
            }
        }
    }
}

