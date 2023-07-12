package com.bankly.core.data.util

import com.bankly.core.model.ChangePassCode
import com.bankly.core.model.ForgotPassCode
import com.bankly.core.model.Message
import com.bankly.core.model.ResetPassCode
import com.bankly.core.model.Status
import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.model.UserWallet
import com.bankly.core.model.ValidateOtp
import com.bankly.core.network.model.AuthenticatedUser
import com.bankly.core.network.model.ResultMessage
import com.bankly.core.network.model.ResultStatus
import com.bankly.core.network.model.WalletResult
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
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

fun ChangePassCode.asRequestBody() = ChangePassCodeRequestBody(
    serialNumber = serialNumber,
    oldPasscode = oldPasscode,
    newPasscode = newPasscode,
    confirmPasscode = confirmPasscode
)

fun ForgotPassCode.asRequestBody() = ForgotPassCodeRequestBody(
    phoneNumber = phoneNumber
)

fun ResetPassCode.asRequestBody() = ResetPassCodeRequestBody(
    username = username, password = password, confirmPassword = confirmPassword, code = code
)

fun ValidateOtp.asRequestBody() = ValidateOtpRequestBody(
    otp = otp, phoneNumber = phoneNumber
)

fun WalletResult.asUserWallet() = UserWallet(
    accountBalance = currentBalance,
    bankName = fundingSourceName,
    accountNumber = fundingAccountNumber,
    accountName = name
)