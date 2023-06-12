package com.bankly.core.domain.repository

import com.bankly.core.common.model.Resource
import com.bankly.core.model.ChangePassCode
import com.bankly.core.model.ForgotPassCode
import com.bankly.core.model.Message
import com.bankly.core.model.ResetPassCode
import com.bankly.core.model.Status
import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.model.ValidateOtp
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getToken(userName: String, password: String): Flow<Resource<Token>>
    suspend fun forgotPassCode(body: ForgotPassCode): Flow<Resource<Status>>
    suspend fun validateOtp(body: ValidateOtp): Flow<Resource<Status>>
    suspend fun resetPassCode(body: ResetPassCode): Flow<Resource<Message>>
    suspend fun changePassCode(body: ChangePassCode): Flow<Resource<User>>
    suspend fun getWallet(token: String): Flow<Resource<Any>>
}