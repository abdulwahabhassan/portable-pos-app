package com.bankly.core.data.util

import com.bankly.core.entity.Bank
import com.bankly.core.entity.Message
import com.bankly.core.entity.AccountNameEnquiry
import com.bankly.core.entity.CableTvNameEnquiry
import com.bankly.core.entity.MeterNameEnquiry
import com.bankly.core.entity.Plan
import com.bankly.core.entity.Provider
import com.bankly.core.entity.Status
import com.bankly.core.entity.Token
import com.bankly.core.entity.User
import com.bankly.core.entity.UserWallet
import com.bankly.core.network.model.response.TokenNetworkResponse
import com.bankly.core.network.model.result.AgentResult
import com.bankly.core.network.model.result.AuthenticatedUserResult
import com.bankly.core.network.model.result.BankResult
import com.bankly.core.network.model.result.PhoneNumberTransactionResult
import com.bankly.core.network.model.result.MessageResult
import com.bankly.core.network.model.result.AccountNameEnquiryResult
import com.bankly.core.network.model.result.StatusResult
import com.bankly.core.network.model.result.AccountNumberTransactionResult
import com.bankly.core.network.model.result.CableTvNameEnquiryResult
import com.bankly.core.network.model.result.MeterNameEnquiryResult
import com.bankly.core.network.model.result.PlanResult
import com.bankly.core.network.model.result.ProviderResult
import com.bankly.core.network.model.result.WalletResult
import com.bankly.core.sealed.TransactionReceipt

fun AuthenticatedUserResult.asUser() = User(
    userId = userId ?: "",
    message = message ?: "",
)

fun TokenNetworkResponse.asToken() = Token(
    token = accessToken ?: "",
    expiresIn = expiresIn ?: 0L,
    tokenType = tokenType ?: "",
)

fun StatusResult.asStatus() = Status(
    status = status ?: false,
)

fun MessageResult.asMessage() = Message(
    message = message ?: "",
)

fun WalletResult.asUserWallet() = UserWallet(
    accountBalance = currentBalance,
    bankName = fundingSourceName,
    accountNumber = fundingAccountNumber,
    accountName = name,
)

fun BankResult.asBank() = Bank(
    name = name,
    id = id,
    categoryId = categoryId ?: -1,
    categoryName = categoryName ?: "",
)

fun AccountNameEnquiryResult.asNameEnquiry() = AccountNameEnquiry(
    accountName = accountName ?: "",
    accountNumber = accountNumber ?: "",
    bankCode = bankCode ?: "",
    bankName = bankName ?: "",
)

fun AgentResult.asNameEnquiry() = AccountNameEnquiry(
    accountName = name ?: "",
    accountNumber = accountNumber ?: "",
    bankCode = "",
    bankName = bankName ?: "",
)

fun AccountNumberTransactionResult.asBankTransfer() = TransactionReceipt.BankTransfer(
    phoneNumber = phoneNumber ?: "",
    amount = amount.toString(),
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
)

fun PhoneNumberTransactionResult.asBankTransfer() = TransactionReceipt.BankTransfer(
    phoneNumber = accountNumber ?: "",
    amount = amount.toString(),
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
)

fun ProviderResult.asProvider() = Provider(
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
    minimumAmount = minimumAmount ?: 0.00
)

fun PlanResult.asPlan() = Plan(
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

fun CableTvNameEnquiryResult.asCableTvNameEnquiry() = CableTvNameEnquiry(
    cardNumber = cardNumber,
    customerName = customerName,
    packageName = packageName
)

fun MeterNameEnquiryResult.asMeterNameEnquiry() = MeterNameEnquiry(
    meterNumber = meterNumber,
    amount = amount,
    customerName = customerName,
    address = address,
    meterType = meterType,
    packageName = packageName,
    debtRepayment = debtRepayment
)