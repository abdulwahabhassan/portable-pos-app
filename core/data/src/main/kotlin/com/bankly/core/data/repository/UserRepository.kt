package com.bankly.core.data.repository

import com.bankly.core.common.model.Resource
import com.bankly.core.model.Message
import com.bankly.core.model.Status
import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getToken(userName: String, password: String): Flow<Resource<Token>>
    suspend fun forgotPassCode(body: ForgotPassCodeRequestBody): Flow<Resource<Status>>
    suspend fun validateOtp(body: ValidateOtpRequestBody): Flow<Resource<Status>>
    suspend fun resetPassCode(body: ResetPassCodeRequestBody): Flow<Resource<Message>>
    suspend fun changePassCode(body: ChangePassCodeRequestBody): Flow<Resource<User>>

}