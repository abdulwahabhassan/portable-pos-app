package com.bankly.core.data.model

import com.bankly.core.model.Message
import com.bankly.core.model.Status
import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.network.model.AuthenticatedUser
import com.bankly.core.network.model.ResultMessage
import com.bankly.core.network.model.ResultStatus
import com.bankly.core.network.model.response.TokenNetworkResponse

fun AuthenticatedUser.asUser() = User(
    userId = userId ?: "",
    message = message ?: ""
)

fun TokenNetworkResponse.asToken() = Token(
    token = accessToken ?: "",
    expiresIn = expiresIn ?: 0L
)

fun ResultStatus.asStatus() = Status(
    status = status ?: false
)

fun ResultMessage.asMessage() = Message(
    message = message ?: ""
)