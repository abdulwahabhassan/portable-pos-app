package com.bankly.core.data.repository

import com.bankly.core.common.model.Resource
import com.bankly.core.common.model.Result
import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun resetPassCode(body: ResetPassCodeRequestBody): Flow<Resource<User>>
    suspend fun forgotPassCode(body: ForgotPassCodeRequestBody): Flow<Resource<User>>
    suspend fun changePassCode(body: ChangePassCodeRequestBody): Flow<Resource<User>>
    suspend fun getToken(userName: String, password: String): Flow<Resource<Token>>
}