package com.bankly.core.data.util

import com.bankly.core.entity.Bank
import com.bankly.core.entity.Message
import com.bankly.core.entity.NameEnquiry
import com.bankly.core.entity.Status
import com.bankly.core.entity.Token
import com.bankly.core.entity.User
import com.bankly.core.entity.UserWallet
import com.bankly.core.network.model.response.TokenNetworkResponse
import com.bankly.core.network.model.result.AgentResult
import com.bankly.core.network.model.result.AuthenticatedUserResult
import com.bankly.core.network.model.result.BankResult
import com.bankly.core.network.model.result.BanklyPhoneTransactionResult
import com.bankly.core.network.model.result.MessageResult
import com.bankly.core.network.model.result.NameEnquiryResult
import com.bankly.core.network.model.result.StatusResult
import com.bankly.core.network.model.result.TransactionResult
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

fun NameEnquiryResult.asNameEnquiry() = NameEnquiry(
    accountName = accountName ?: "",
    accountNumber = accountNumber ?: "",
    bankCode = bankCode ?: "",
    bankName = bankName ?: "",
)

fun AgentResult.asNameEnquiry() = NameEnquiry(
    accountName = name ?: "",
    accountNumber = accountNumber ?: "",
    bankCode = "",
    bankName = bankName ?: "",
)

fun TransactionResult.asBankTransfer() = TransactionReceipt.BankTransfer(
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

fun BanklyPhoneTransactionResult.asBankTransfer() = TransactionReceipt.BankTransfer(
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
