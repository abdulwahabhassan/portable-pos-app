package com.bankly.core.data.util

import com.bankly.core.model.ChangePassCode
import com.bankly.core.model.ExternalTransfer
import com.bankly.core.model.ForgotPassCode
import com.bankly.core.model.InternalTransfer
import com.bankly.core.model.ResetPassCode
import com.bankly.core.model.ValidateOtp
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ExternalTransferRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.InternalTransferRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody

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

fun InternalTransfer.asRequestBody() = InternalTransferRequestBody(
    amount = amount,
    recipientAccount = recipientAccount,
    pin = pin,
    otp = otp,
    securityQuestionId = securityQuestionId,
    securityQuestionResponse = securityQuestionResponse,
    clientRequestId = clientRequestId,
    deviceId = deviceId
)

fun ExternalTransfer.asRequestBody() = ExternalTransferRequestBody(
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