package com.bankly.core.data.util

import com.bankly.core.data.BankTransferData
import com.bankly.core.data.BillPaymentData
import com.bankly.core.data.CardTransferAccountInquiryData
import com.bankly.core.data.CardTransferData
import com.bankly.core.data.ChangePassCodeData
import com.bankly.core.data.EodTransactionListData
import com.bankly.core.data.ForgotPassCodeData
import com.bankly.core.data.ForgotTerminalAccessPinData
import com.bankly.core.data.GetRecentFundingData
import com.bankly.core.data.ResetPassCodeData
import com.bankly.core.data.SendReceiptData
import com.bankly.core.data.SyncRecentFundingData
import com.bankly.core.data.ValidateCableTvNumberData
import com.bankly.core.data.ValidateElectricityMeterNumberData
import com.bankly.core.data.ValidateOtpData
import com.bankly.core.network.model.request.BankTransferRequestBody
import com.bankly.core.network.model.request.BillPaymentRequestBody
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.GetRecentFundingRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.SendReceiptRequestBody
import com.bankly.core.network.model.request.SyncRecentFundingRequestBody
import com.bankly.core.data.TransactionFilterData
import com.bankly.core.network.model.request.CardTransferAccountInquiryRequestBody
import com.bankly.core.network.model.request.CardTransferRequestBody
import com.bankly.core.network.model.request.ForgotTerminalAccessPinRequestBody
import com.bankly.core.network.model.request.SyncEodRequestBody
import com.bankly.core.network.model.request.SyncEodTransactionData
import com.bankly.core.network.model.request.ValidateCableTvNumberRequestBody
import com.bankly.core.network.model.request.ValidateElectricityMeterNumberRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun ChangePassCodeData.asRequestBody() = ChangePassCodeRequestBody(
    serialNumber = serialNumber,
    oldPasscode = oldPasscode,
    newPasscode = newPasscode,
    confirmPasscode = confirmPasscode,
)

fun ForgotPassCodeData.asRequestBody() = ForgotPassCodeRequestBody(
    phoneNumber = phoneNumber,
)

fun ForgotTerminalAccessPinData.asRequestBody() = ForgotTerminalAccessPinRequestBody(
    serialNumber = serialNumber,
)


fun ResetPassCodeData.asRequestBody() = ResetPassCodeRequestBody(
    username = username,
    password = password,
    confirmPassword = confirmPassword,
    code = code,
)

fun ValidateOtpData.asRequestBody() = ValidateOtpRequestBody(
    otp = otp,
    phoneNumber = phoneNumber,
)

fun BankTransferData.asRequestBody(): BankTransferRequestBody = when (this) {
    is BankTransferData.AccountNumber -> BankTransferRequestBody.AccountNumber(
        accountName = accountName,
        accountNumber = accountNumber,
        bankId = bankId,
        bankName = bankName,
        narration = narration,
        phoneNumber = phoneNumber,
        amountToSend = amountToSend,
        otp = otp,
        channel = channel,
        clientRequestId = clientRequestId,
        securityQuestionId = securityQuestionId,
        securityQuestionResponse = securityQuestionResponse,
        deviceId = deviceId,
        isWeb = isWeb,
        senderName = senderName,
    )

    is BankTransferData.PhoneNumber -> BankTransferRequestBody.PhoneNumber(
        amount = amount,
        recipientAccount = recipientAccount,
        pin = pin,
        otp = otp,
        securityQuestionId = securityQuestionId,
        securityQuestionResponse = securityQuestionResponse,
        clientRequestId = clientRequestId,
        deviceId = deviceId,
        channel = channel,
    )
}

fun BillPaymentData.asRequestBody() = BillPaymentRequestBody(
    billItemId = billItemId,
    billId = billId,
    amount = amount,
    currency = currency,
    paidFor = paidFor,
    otp = otp,
    channel = channel,
    serialNumber = serialNumber,
    terminalId = terminalId,
    clientRequestId = clientRequestId,
    deviceId = deviceId,
    paidForPhone = paidForPhone,
    paidForName = paidForName
)

fun ValidateCableTvNumberData.asRequestBody() = ValidateCableTvNumberRequestBody(
    cardNumber = cardNumber,
    billId = billId
)

fun ValidateElectricityMeterNumberData.asRequestBody() = ValidateElectricityMeterNumberRequestBody(
    meterNumber = meterNumber,
    billId = billId,
    billItemId = billItemId
)

fun GetRecentFundingData.asRequestBody() = GetRecentFundingRequestBody(
    updateOnFetch = updateOnFetch, serialNumber = serialNumber, location = location
)

fun SendReceiptData.asRequestBody() = SendReceiptRequestBody(
    sessionId = sessionId, beneficiary = beneficiary, routeType = routeType
)

fun SyncRecentFundingData.asRequestBody() = SyncRecentFundingRequestBody(
    sessionId = sessionId, serialNumber = serialNumber, location = location
)

fun TransactionFilterData.asRequestParam() = Json.encodeToString(this)

fun EodTransactionListData.asRequestBody() = SyncEodRequestBody(
    transactions = this.map {
        SyncEodTransactionData(
            it.serialNumber,
            it.transactionReference,
            it.paymentDate,
            it.responseCode,
            it.stan,
            it.responseMessage,
            it.amount,
            it.maskedPan,
            it.terminalId,
            it.rrn,
            it.transType
        )
    }
)

fun CardTransferAccountInquiryData.asRequestBody() = CardTransferAccountInquiryRequestBody(
    bankId = bankId,
    accountNumber = accountNumber,
    amount = amount,
    serialNumber = serialNumber,
    terminalId = terminalId,
    channel = channel,
    geoLocation = geoLocation,
    deviceType = deviceType
)

fun CardTransferData.asRequestBody() = CardTransferRequestBody(
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