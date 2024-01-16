package com.bankly.core.data.util

import com.bankly.core.database.model.EodTransaction
import com.bankly.core.database.model.Notification
import com.bankly.core.model.entity.AccountNameEnquiry
import com.bankly.core.model.entity.AddDeviceToken
import com.bankly.core.model.entity.AgentAccountDetails
import com.bankly.core.model.entity.Bank
import com.bankly.core.model.entity.BankNetwork
import com.bankly.core.model.entity.BillPlan
import com.bankly.core.model.entity.BillProvider
import com.bankly.core.model.entity.CableTvNameEnquiry
import com.bankly.core.model.entity.CardTransferAccountInquiry
import com.bankly.core.model.entity.EodInfo
import com.bankly.core.model.entity.Message
import com.bankly.core.model.entity.MeterNameEnquiry
import com.bankly.core.model.entity.NotificationMessage
import com.bankly.core.model.entity.RecentFund
import com.bankly.core.model.entity.Status
import com.bankly.core.model.entity.SyncEod
import com.bankly.core.model.entity.Token
import com.bankly.core.model.entity.TransactionFilterType
import com.bankly.core.model.entity.Transaction
import com.bankly.core.model.entity.User
import com.bankly.core.model.entity.UserWallet
import com.bankly.core.network.model.response.TokenApiResponse
import com.bankly.core.network.model.result.AccountNameEnquiryResult
import com.bankly.core.network.model.result.AccountNumberTransactionResult
import com.bankly.core.network.model.result.AddDeviceTokenResult
import com.bankly.core.network.model.result.AgentAccountResult
import com.bankly.core.network.model.result.AgentResult
import com.bankly.core.network.model.result.AuthenticatedUserResult
import com.bankly.core.network.model.result.BankNetworkResult
import com.bankly.core.network.model.result.BankResult
import com.bankly.core.network.model.result.BillPaymentResult
import com.bankly.core.network.model.result.CableTvNameEnquiryResult
import com.bankly.core.network.model.result.CardTransferAccountInquiryResult
import com.bankly.core.network.model.result.CardTransferTransactionResult
import com.bankly.core.network.model.result.EodInfoResult
import com.bankly.core.network.model.result.MessageResult
import com.bankly.core.network.model.result.MeterNameEnquiryResult
import com.bankly.core.network.model.result.PhoneNumberTransactionResult
import com.bankly.core.network.model.result.PlanResult
import com.bankly.core.network.model.result.ProviderResult
import com.bankly.core.network.model.result.RecentFundResult
import com.bankly.core.network.model.result.StatusResult
import com.bankly.core.network.model.result.SyncEodResult
import com.bankly.core.network.model.result.TransactionFilterTypeResult
import com.bankly.core.network.model.result.TransactionResult
import com.bankly.core.network.retrofit.model.Any
import com.bankly.core.model.sealed.TransactionReceipt

fun Any.toAny() = Any()

fun AuthenticatedUserResult.asUser() = User(
    userId = userId ?: "",
    message = message ?: "",
)

fun TokenApiResponse.asToken() = com.bankly.core.model.entity.Token(
    token = accessToken ?: "",
    expiresIn = expiresIn ?: 0L,
    tokenType = tokenType ?: "",
)

fun StatusResult.asStatus() = com.bankly.core.model.entity.Status(
    status = status ?: false,
)

fun MessageResult.asMessage() = com.bankly.core.model.entity.Message(
    message = message ?: "",
)

fun AgentAccountResult.asUserWallet() = UserWallet(
    accountBalance = currentBalance,
    bankName = fundingSourceName,
    accountNumber = fundingAccountNumber,
    accountName = name,
)

fun AgentAccountResult.asAgentAccountDetails() = com.bankly.core.model.entity.AgentAccountDetails(
    fundingAccountNumber = fundingAccountNumber,
    name = name,
    fundingSourceName = fundingSourceName,
)

fun BankResult.asBank() = com.bankly.core.model.entity.Bank(
    name = name,
    id = id,
    categoryId = categoryId ?: -1,
    categoryName = categoryName ?: "",
)

fun AccountNameEnquiryResult.asNameEnquiry() = com.bankly.core.model.entity.AccountNameEnquiry(
    accountName = accountName ?: "",
    accountNumber = accountNumber ?: "",
    bankCode = bankCode ?: "",
    bankName = bankName ?: "",
)

fun CardTransferAccountInquiryResult.asAccountInquiry() =
    com.bankly.core.model.entity.CardTransferAccountInquiry(
        accountName = accountName ?: "",
        inquiryReference = inquiryReference ?: "",
        balance = balance ?: 0.00,
        reference = reference ?: "",
        accountNumber = accountNumber ?: "",
        bankCode = bankCode ?: "",
        bankId = bankId ?: 0,
        bankName = bankName ?: "",
    )

fun AgentResult.asNameEnquiry() = com.bankly.core.model.entity.AccountNameEnquiry(
    accountName = name ?: "",
    accountNumber = accountNumber ?: "",
    bankCode = "",
    bankName = bankName ?: "",
)

fun AccountNumberTransactionResult.asBankTransfer() = TransactionReceipt.BankTransfer(
    phoneNumber = phoneNumber ?: "",
    amount = amount ?: 0.00,
    reference = reference ?: "",
    accountNumber = accountNumber ?: "",
    sourceWallet = sourceWallet ?: 0,
    paymentGateway = paymentGateway ?: 0,
    message = message ?: "",
    bankName = bankName ?: "",
    beneficiaryAccount = beneficiaryAccount ?: "",
    accountName = accountName ?: "",
    sourceWalletName = sourceWalletName ?: "",
    dateCreated = dateCreated ?: "",
    statusName = statusName ?: "",
    sessionId = sessionId ?: "",
    isIntra = false
)

fun PhoneNumberTransactionResult.asBankTransfer() = TransactionReceipt.BankTransfer(
    phoneNumber = accountNumber ?: "",
    amount = amount ?: 0.00,
    reference = reference ?: "",
    accountNumber = accountNumber ?: "",
    sourceWallet = 0,
    paymentGateway = 0,
    message = description ?: "",
    bankName = recipientBank ?: "",
    beneficiaryAccount = recipientAccountNo ?: "",
    accountName = accountName ?: "",
    sourceWalletName = accountName ?: "",
    dateCreated = transferredOn ?: "",
    statusName = "",
    sessionId = "",
    isIntra = transferType == "Intra"
)

fun CardTransferTransactionResult.asCardTransfer() = TransactionReceipt.CardTransfer(
    beneficiaryAccountNumber = accountNumber ?: "",
    beneficiaryBankName = bankName ?: "",
    amount = amount ?: 0.00,
    reference = reference ?: "",
    message = message ?: "",
    dateCreated = dateCreated ?: "",
    statusName = statusName ?: "",
    sessionId = sessionId ?: "",
    terminalId = "",
    cardType = "",
    cardNumber = "",
    beneficiaryName = accountName ?: "",
    responseCode = "",
    rrn = "",
    stan = ""
)

fun ProviderResult.asProvider() = com.bankly.core.model.entity.BillProvider(
    id = id ?: 0,
    categoryId = categoryId ?: 0,
    code = code ?: "",
    name = name ?: "",
    description = description ?: "",
    amount = amount ?: 0.00,
    hasItems = hasItems ?: false,
    active = active ?: false,
    billType = billType ?: 0,
    provider = provider ?: 0,
    transactionChargeCommissionType = transactionChargeCommissionType ?: 0,
    transactionCharge = transactionCharge ?: 0.00,
    agentCommissionType = agentCommissionType ?: 0,
    agentCommissionValue = agentCommissionValue ?: 0.00,
    aggregatorCommissionType = aggregatorCommissionType ?: 0,
    aggregatorCommissionValue = aggregatorCommissionValue ?: 0.00,
    providerPercentage = providerPercentage ?: 0.00,
    billImageUrl = billImageUrl ?: "",
    providerAmount = providerAmount ?: 0.00,
    providerId = providerId ?: 0,
    dateCreated = dateCreated ?: "",
    minimumAmount = minimumAmount ?: 0.00,
)

fun PlanResult.asPlan() = com.bankly.core.model.entity.BillPlan(
    id ?: 0,
    billId ?: 0,
    code ?: "",
    name ?: "",
    description ?: "",
    amount ?: 0.00,
    active ?: false,
    hasFixedAmount ?: false,
    minimumAmount ?: 0.00,
    isTokenType ?: false,
    available ?: 0,
)

fun CableTvNameEnquiryResult.asCableTvNameEnquiry() =
    com.bankly.core.model.entity.CableTvNameEnquiry(
        cardNumber = cardNumber,
        customerName = customerName,
        packageName = packageName,
    )

fun MeterNameEnquiryResult.asMeterNameEnquiry() = com.bankly.core.model.entity.MeterNameEnquiry(
    meterNumber = meterNumber,
    amount = amount,
    customerName = customerName,
    address = address,
    meterType = meterType,
    packageName = packageName,
    debtRepayment = debtRepayment,
)

fun BillPaymentResult.asBillPayment() = TransactionReceipt.BillPayment(
    id = id,
    reference = reference ?: "",
    narration = narration ?: "",
    description = description ?: "",
    amount = amount ?: 0.00,
    paymentType = paymentType ?: "",
    paidFor = paidFor ?: "",
    paidForName = paidForName ?: "",
    paidByAccountId = paidByAccountId ?: 0L,
    paidByAccountNo = paidByAccountNo ?: "",
    paidByAccountName = paidByAccountName ?: "",
    paidOn = paidOn ?: "",
    polled = polled ?: false,
    responseStatus = responseStatus ?: 0L,
    transactionType = transactionType ?: "",
    billName = billName ?: "",
    billItemName = billItemName ?: "",
    receiver = receiver ?: "",
    commission = commission ?: 0.00,
    billToken = billToken ?: "",
    isTokenType = isTokenType ?: false,
)

fun RecentFundResult.asRecentFund() = com.bankly.core.model.entity.RecentFund(
    transactionReference = transactionReference ?: "",
    amount = amount ?: 0.00,
    accountReference = accountReference ?: "",
    paymentDescription = paymentDescription ?: "",
    transactionHash = transactionHash ?: "",
    senderAccountNumber = senderAccountNumber ?: "",
    senderAccountName = senderAccountName ?: "",
    sessionId = sessionId ?: "",
    phoneNumber = phoneNumber ?: "",
    userId = userId ?: "",
    transactionDate = transactionDate ?: "",
    senderBankName = senderBankName ?: "",
    receiverBankName = receiverBankName ?: "",
    receiverAccountNumber = receiverAccountNumber ?: "",
    receiverAccountName = receiverAccountName ?: "",
)

fun TransactionResult.asTransaction() = com.bankly.core.model.entity.Transaction.History(
    creditAccountNo = creditAccountNo ?: "",
    debitAccountNo = debitAccountNo ?: "",
    transactionBy = transactionBy ?: "",
    channelName = channelName ?: "",
    statusName = statusName ?: "",
    userType = userType ?: 0,
    userId = userId ?: 0,
    initiator = initiator ?: "",
    archived = archived ?: false,
    product = product ?: "",
    hasProduct = hasProduct ?: false,
    senderName = senderName ?: "",
    receiverName = receiverName ?: "",
    balanceBeforeTransaction = balanceBeforeTransaction ?: 0.00,
    leg = leg ?: 0,
    id = id ?: 0,
    reference = reference ?: "",
    transactionType = transactionType ?: 0,
    transactionTypeName = transactionTypeName ?: "",
    description = description?.trim() ?: "",
    narration = narration ?: "",
    amount = amount ?: 0.00,
    creditAccountNumber = creditAccountNumber ?: "",
    deditAccountNumber = deditAccountNumber ?: "",
    parentReference = parentReference ?: "",
    transactionDate = transactionDate ?: "",
    credit = credit ?: 0.00,
    debit = debit ?: 0.00,
    balanceAfterTransaction = balanceAfterTransaction ?: 0.00,
    sender = sender ?: "",
    receiver = receiver ?: "",
    channel = channel ?: 0,
    status = status ?: 0,
    charges = charges ?: 0.00,
    aggregatorCommission = aggregatorCommission ?: 0.00,
    hasCharges = hasCharges ?: false,
    agentCommission = agentCommission ?: 0.00,
    debitAccountNumber = debitAccountNumber ?: "",
    initiatedBy = initiatedBy ?: "",
    stateId = stateId ?: 0,
    lgaId = lgaId ?: 0,
    regionId = regionId ?: "",
    aggregatorId = aggregatorId ?: 0,
    isCredit = isCredit ?: false,
    isDebit = isDebit ?: false,
)

fun TransactionFilterTypeResult.asTransactionFilterType() = TransactionFilterType(
    name = name,
    id = id,
    isSelected = false,
)

fun BankNetworkResult.asBankNetwork() = com.bankly.core.model.entity.BankNetwork(
    bankName = bankName ?: "",
    bankIcon = "",
    networkPercentage = countPercentage ?: 0.00,
    totalCount = totalCount ?: 0,
)

fun EodInfoResult.asEodInfo() = com.bankly.core.model.entity.EodInfo(
    settled = settled ?: 0.00,
    availableBalance = availableBalance ?: 0.00,
    responseMessage = responseMessage ?: "",
    notificationId = notificationId ?: 0,
    responseCode = responseCode ?: "",
    terminalId = terminalId ?: "",
    balance = balance ?: 0.00,
    amountAdded = amountAdded ?: 0.00,
)

fun SyncEodResult.asSyncEod() = com.bankly.core.model.entity.SyncEod(
    responseMessage = responseMessage ?: "",
    notificationId = notificationId ?: 0,
    responseCode = responseCode ?: "",
    terminalId = terminalId ?: "",
    balance = balance ?: 0.00,
    amountAdded = amountAdded ?: 0.00,
)


fun TransactionReceipt.asEodTransaction(): EodTransaction? {
    return when (this) {
        is TransactionReceipt.BankTransfer -> EodTransaction.BankTransfer(
            beneficiaryAccountName = accountName,
            beneficiaryBankName = bankName,
            amount = amount,
            reference = reference,
            senderPhoneNumber = phoneNumber,
            message = message,
            beneficiaryAccountNumber = accountNumber,
            dateCreated = dateCreated,
            statusName = statusName,
            sessionId = sessionId,
            isIntra = isIntra
        )

        is TransactionReceipt.BillPayment -> EodTransaction.BillPayment(
            id = id,
            reference = reference,
            narration = narration,
            description = description,
            amount = amount,
            paymentType = paymentType,
            paidFor = paidFor,
            paidForName = paidForName,
            paidByAccountId = paidByAccountId,
            paidByAccountNo = paidByAccountNo,
            paidByAccountName = paidByAccountName,
            paidOn = paidOn,
            polled = polled,
            responseStatus = responseStatus,
            transactionType = transactionType,
            billName = billName,
            billItemName = billItemName,
            receiver = receiver,
            commission = commission,
            billToken = billToken,
            isTokenType = isTokenType,
            statusName = ""
        )

        is TransactionReceipt.CardPayment -> EodTransaction.CardPayment(
            cardHolderName = cardHolderName,
            cardNumber = cardNumber,
            cardType = cardType,
            amount = amount,
            reference = reference,
            statusName = statusName,
            message = message,
            dateTime = dateTime,
            rrn = rrn,
            stan = stan,
            terminalId = terminalId,
            responseCode = responseCode
        )

        is TransactionReceipt.CardTransfer -> EodTransaction.CardTransfer(
            beneficiaryAccountNumber = beneficiaryAccountNumber,
            beneficiaryBankName = beneficiaryBankName,
            beneficiaryName = "",
            senderAccountNumber = "",
            senderBankName = "",
            senderName = "",
            amount = amount,
            reference = reference,
            message = message,
            dateCreated = dateCreated,
            statusName = statusName,
            sessionId = sessionId,
            terminalId = terminalId,
            cardType = cardType,
            cardNumber = cardNumber,
            responseCode = responseCode,
            rrn = rrn,
            stan = stan
        )

        is TransactionReceipt.PayWithTransfer -> EodTransaction.PayWithTransfer(
            senderAccountName = senderAccountName,
            senderAccountNumber = senderAccountNumber,
            senderBankName = senderBankName,
            amount = amount,
            reference = reference,
            receiverAccountNumber = receiverAccountNumber,
            message = message,
            receiverName = receiverName,
            receiverBankName = receiverBankName,
            dateCreated = dateCreated,
            statusName = statusName,
            sessionId = sessionId
        )

        is TransactionReceipt.History -> null
    }
}

fun com.bankly.core.model.entity.NotificationMessage.toNotification(): Notification {
    return Notification(
        title = title, dateTime = dateTime, message = message, seen = seen
    )
}

fun Notification.toNotificationMessage(): com.bankly.core.model.entity.NotificationMessage {
    return com.bankly.core.model.entity.NotificationMessage(
        title = this.title,
        message = this.message,
        dateTime = this.dateTime,
        seen = this.seen
    )
}

fun AddDeviceTokenResult.asDeviceToken(): com.bankly.core.model.entity.AddDeviceToken =
    com.bankly.core.model.entity.AddDeviceToken(id, userId, deviceId, provider)