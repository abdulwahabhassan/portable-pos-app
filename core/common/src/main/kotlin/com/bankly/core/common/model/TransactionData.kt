package com.bankly.core.common.model

import com.bankly.core.common.util.Formatter
import com.bankly.core.common.util.clientReqId
import com.bankly.core.data.BankTransferData
import com.bankly.core.data.BillPaymentData
import com.bankly.core.data.CardTransferData
import com.bankly.core.enums.BillsProviderType
import kotlinx.serialization.Serializable

@Serializable
sealed class TransactionData(
    val transactionAmount: Double,
    var pin: String,
) {
    @Serializable
    data class BankTransfer(
        val accountNumber: String,
        val accountName: String,
        val amount: Double,
        val bankName: String,
        val bankId: String,
        val narration: String,
        val accountNumberType: AccountNumberType,
        val transactionPin: String,
        val vat: Double = 0.00,
        val fee: Double = 0.00,
    ) : TransactionData(transactionAmount = amount, pin = transactionPin) {
        fun toBankTransferData(): BankTransferData {
            return when (accountNumberType) {
                AccountNumberType.ACCOUNT_NUMBER -> {
                    BankTransferData.AccountNumber(
                        accountName = accountName,
                        accountNumber = accountNumber,
                        bankId = bankId,
                        bankName = bankName,
                        narration = narration,
                        amountToSend = amount.toString(),
                        otp = pin,
                        clientRequestId = clientReqId
                    )
                }

                AccountNumberType.PHONE_NUMBER -> {
                    BankTransferData.PhoneNumber(
                        amount = amount.toString(),
                        recipientAccount = accountNumber,
                        otp = pin,
                        clientRequestId = clientReqId
                    )
                }
            }
        }
    }

    @Serializable
    data class BillPayment(
        val billsProviderType: BillsProviderType,
        val phoneNumber: String,
        val amount: Double,
        val transactionPin: String,
        val billItemId: String,
        val billId: String,
        val currency: String = "NGN",
        val channel: String = "4",
        val terminalSerialNumber: String = "",
        val terminalId: String = "",
        val clientRequestId: String = clientReqId,
        val billProvider: String,
        val billPlan: String,
        val cableTvNumberOrMeterNumber: String,
        val cableTvOwnerNameOrMeterOwnerName: String,
    ) : TransactionData(transactionAmount = amount, pin = transactionPin) {
        fun toBillPaymentData(): BillPaymentData {
            return BillPaymentData(
                billItemId = billItemId,
                billId = billId,
                amount = amount.toString(),
                currency = currency,
                paidFor = when (billsProviderType) {
                    BillsProviderType.ELECTRICITY, BillsProviderType.CABLE_TV -> cableTvNumberOrMeterNumber
                    BillsProviderType.AIRTIME, BillsProviderType.INTERNET_DATA -> phoneNumber
                },
                otp = pin,
                channel = channel,
                serialNumber = terminalSerialNumber,
                terminalId = terminalId,
                clientRequestId = clientRequestId,
                deviceId = terminalSerialNumber,
                paidForPhone = phoneNumber,
                paidForName = cableTvOwnerNameOrMeterOwnerName
            )
        }
    }

    @Serializable
    data class CardTransfer(
        val accountName: String,
        val inquiryReference: String,
        val accountNumber: String,
        val amount: Double,
        val narration: String = "null",
        val channel: String = "4",
        val sendersPhoneNumber: String,
        val clientRequestId: String = clientReqId,
        val responseCode: String = "",
        val responseMessage: String = ""
    ) : TransactionData(transactionAmount = amount, pin = "") {
        fun toCardTransferData() = CardTransferData(
            accountName = accountName,
            inquiryReference = inquiryReference,
            accountNumber = accountNumber,
            amount = amount,
            narration = narration,
            channel = channel,
            sendersPhoneNumber = sendersPhoneNumber,
            clientRequestId = clientRequestId,
            responseCode = responseCode,
            responseMessage = responseMessage
        )
    }

    fun toTransactionSummaryMap(): Map<String, String> {
        return when (this) {
            is BankTransfer -> {
                mapOf(
                    when (accountNumberType) {
                        AccountNumberType.ACCOUNT_NUMBER -> "Account Number" to accountNumber
                        AccountNumberType.PHONE_NUMBER -> "Phone Number" to accountNumber
                    },
                    "Account Name" to accountName,
                    "Amount" to Formatter.formatAmount(
                        value = transactionAmount.toString(),
                        includeNairaSymbol = true,
                    ),
                    "VAT" to Formatter.formatAmount(
                        value = vat.toString(),
                        includeNairaSymbol = true,
                    ),
                    "Fee" to Formatter.formatAmount(
                        value = fee.toString(),
                        includeNairaSymbol = true,
                    ),
                )
            }

            is BillPayment -> {
                mapOf(
                    "Bill Type" to billsProviderType.title,
                    "Provider" to billProvider,
                    "Plan" to billPlan,
                    when (billsProviderType) {
                        BillsProviderType.ELECTRICITY -> "Meter Number"
                        BillsProviderType.CABLE_TV -> "IUC/Decoder Number"
                        else -> ""
                    } to cableTvNumberOrMeterNumber,
                    "Phone Number" to phoneNumber,
                    "Amount" to Formatter.formatAmount(
                        value = transactionAmount.toString(),
                        includeNairaSymbol = true,
                    ),
                )
            }

            is CardTransfer -> {
                mapOf(
                    "Account Number" to accountNumber,
                    "Account Name" to accountName,
                    "Amount" to Formatter.formatAmount(
                        value = transactionAmount.toString(),
                        includeNairaSymbol = true,
                    ),
                    "VAT" to Formatter.formatAmount(
                        value = "0.00",
                        includeNairaSymbol = true,
                    ),
                    "Fee" to Formatter.formatAmount(
                        value = "0.00",
                        includeNairaSymbol = true,
                    ),
                )
            }
        }
    }
}




