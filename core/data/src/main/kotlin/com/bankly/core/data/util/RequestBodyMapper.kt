package com.bankly.core.data.util

import com.bankly.core.data.AccountNumberTransferData
import com.bankly.core.data.ChangePassCodeData
import com.bankly.core.data.ForgotPassCodeData
import com.bankly.core.data.PhoneNumberTransferData
import com.bankly.core.data.ResetPassCodeData
import com.bankly.core.data.ValidateOtpData
import com.bankly.core.network.model.request.AccountNumberTransferRequestBody
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.PhoneNumberTransferRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody

fun ChangePassCodeData.asRequestBody() = ChangePassCodeRequestBody(
    serialNumber = serialNumber,
    oldPasscode = oldPasscode,
    newPasscode = newPasscode,
    confirmPasscode = confirmPasscode
)

fun ForgotPassCodeData.asRequestBody() = ForgotPassCodeRequestBody(
    phoneNumber = phoneNumber
)

fun ResetPassCodeData.asRequestBody() = ResetPassCodeRequestBody(
    username = username, password = password, confirmPassword = confirmPassword, code = code
)

fun ValidateOtpData.asRequestBody() = ValidateOtpRequestBody(
    otp = otp, phoneNumber = phoneNumber
)

fun PhoneNumberTransferData.asRequestBody() = PhoneNumberTransferRequestBody(
    amount = amount,
    recipientAccount = recipientAccount,
    pin = pin,
    otp = otp,
    securityQuestionId = securityQuestionId,
    securityQuestionResponse = securityQuestionResponse,
    clientRequestId = clientRequestId,
    deviceId = deviceId,
    channel = channel
)

fun AccountNumberTransferData.asRequestBody() = AccountNumberTransferRequestBody(
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
    senderName = senderName
)