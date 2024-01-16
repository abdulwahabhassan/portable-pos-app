package com.bankly.core.domain.repository

import com.bankly.core.model.data.ChangePassCodeData
import com.bankly.core.model.data.ForgotPassCodeData
import com.bankly.core.model.data.ForgotTerminalAccessPinData
import com.bankly.core.model.data.ResetPassCodeData
import com.bankly.core.model.data.ValidateOtpData
import com.bankly.core.model.entity.AgentAccountDetails
import com.bankly.core.model.entity.Message
import com.bankly.core.model.entity.Status
import com.bankly.core.model.entity.Token
import com.bankly.core.model.entity.User
import com.bankly.core.model.entity.UserWallet
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getToken(userName: String, password: String): Flow<Resource<com.bankly.core.model.entity.Token>>
    suspend fun forgotPassCode(body: com.bankly.core.model.data.ForgotPassCodeData): Flow<Resource<com.bankly.core.model.entity.Status>>
    suspend fun forgotTerminalAccessPin(body: com.bankly.core.model.data.ForgotTerminalAccessPinData): Flow<Resource<com.bankly.core.model.entity.Status>>
    suspend fun validateOtp(body: com.bankly.core.model.data.ValidateOtpData): Flow<Resource<com.bankly.core.model.entity.Status>>
    suspend fun resetPassCode(body: com.bankly.core.model.data.ResetPassCodeData): Flow<Resource<com.bankly.core.model.entity.Message>>
    suspend fun changePassCode(body: com.bankly.core.model.data.ChangePassCodeData): Flow<Resource<User>>
    suspend fun getWallet(token: String): Flow<Resource<UserWallet>>
    suspend fun getAgentAccountDetails(token: String): Flow<Resource<com.bankly.core.model.entity.AgentAccountDetails>>
    suspend fun validatePassCode(password: String, token: String): Flow<Resource<com.bankly.core.model.entity.Token>>
}
