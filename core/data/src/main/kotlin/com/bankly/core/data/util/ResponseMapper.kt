package com.bankly.core.data.util

import com.bankly.core.model.Bank
import com.bankly.core.model.Message
import com.bankly.core.model.NameEnquiry
import com.bankly.core.model.Status
import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.model.UserWallet
import com.bankly.core.network.model.AuthenticatedUser
import com.bankly.core.network.model.BankResult
import com.bankly.core.network.model.NameEnquiryResult
import com.bankly.core.network.model.ResultMessage
import com.bankly.core.network.model.ResultStatus
import com.bankly.core.network.model.WalletResult
import com.bankly.core.network.model.response.TokenNetworkResponse

fun AuthenticatedUser.asUser() = User(
    userId = userId ?: "",
    message = message ?: ""
)

fun TokenNetworkResponse.asToken() = Token(
    token = accessToken ?: "",
    expiresIn = expiresIn ?: 0L,
    tokenType = tokenType ?: ""
)

fun ResultStatus.asStatus() = Status(
    status = status ?: false
)

fun ResultMessage.asMessage() = Message(
    message = message ?: ""
)


fun WalletResult.asUserWallet() = UserWallet(
    accountBalance = currentBalance,
    bankName = fundingSourceName,
    accountNumber = fundingAccountNumber,
    accountName = name
)

fun BankResult.asBank() = Bank(
    name = name,
    id = id,
    categoryId = categoryId ?: -1,
    categoryName = categoryName ?: ""
)

fun NameEnquiryResult.asNameEnquiry() = NameEnquiry(
    accountName = accountName ?: "",
    accountNumber = accountNumber ?: "",
    bankCode = bankCode ?: "",
    bankName = bankName ?: ""
)
