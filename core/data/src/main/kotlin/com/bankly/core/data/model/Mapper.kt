package com.bankly.core.data.model

import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.network.model.AuthenticatedUser
import com.bankly.core.network.model.response.TokenNetworkResponse

fun AuthenticatedUser.asUser() = User(
    userId = userId ?: "",
    message = message ?: ""
)

fun TokenNetworkResponse.asToken() = Token(
    token = accessToken ?: "",
    expiresIn = expiresIn ?: 0L
)