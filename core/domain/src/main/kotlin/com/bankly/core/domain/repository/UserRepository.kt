package com.bankly.core.domain.repository

import com.bankly.core.data.ChangePassCodeData
import com.bankly.core.data.ForgotPassCodeData
import com.bankly.core.data.ResetPassCodeData
import com.bankly.core.data.ValidateOtpData
import com.bankly.core.entity.AgentAccountDetails
import com.bankly.core.entity.Message
import com.bankly.core.entity.Status
import com.bankly.core.entity.Token
import com.bankly.core.entity.User
import com.bankly.core.entity.UserWallet
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getToken(userName: String, password: String): Flow<Resource<Token>>
    suspend fun forgotPassCode(body: ForgotPassCodeData): Flow<Resource<Status>>
    suspend fun validateOtp(body: ValidateOtpData): Flow<Resource<Status>>
    suspend fun resetPassCode(body: ResetPassCodeData): Flow<Resource<Message>>
    suspend fun changePassCode(body: ChangePassCodeData): Flow<Resource<User>>
    suspend fun getWallet(token: String): Flow<Resource<UserWallet>>
    suspend fun getAgentAccountDetails(token: String): Flow<Resource<AgentAccountDetails>>
}
