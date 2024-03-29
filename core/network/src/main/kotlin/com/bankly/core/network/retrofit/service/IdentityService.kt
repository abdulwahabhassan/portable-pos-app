package com.bankly.core.network.retrofit.service

import com.bankly.core.network.BuildConfig.AUTH_MODE
import com.bankly.core.network.BuildConfig.CLIENT_ID
import com.bankly.core.network.BuildConfig.CLIENT_SECRET
import com.bankly.core.network.BuildConfig.GRANT_TYPE
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ForgotTerminalAccessPinRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import com.bankly.core.network.model.response.ApiResponse
import com.bankly.core.network.model.response.TokenApiResponse
import com.bankly.core.network.model.result.AuthenticatedUserResult
import com.bankly.core.network.model.result.MessageResult
import com.bankly.core.network.model.result.StatusResult
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

interface IdentityService {
    @POST(value = "post/api/Account/password/v2/forgotpasswordcode")
    suspend fun forgotPassCode(
        @Body body: ForgotPassCodeRequestBody,
    ): ApiResponse<StatusResult>

    @PUT(value = "post/api/Account/validateotp")
    suspend fun validateOtp(
        @Body body: ValidateOtpRequestBody,
    ): ApiResponse<StatusResult>

    @FormUrlEncoded
    @POST(value = "post/connect/token")
    suspend fun getToken(
        @Field("client_id") clientId: String = CLIENT_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET,
        @Field("grant_type") grantType: String = GRANT_TYPE,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("auth_mode") authMode: String? = AUTH_MODE,
    ): TokenApiResponse

    @PUT(value = "put/TerminalPasscode/Change")
    suspend fun changePassCode(
        @Body body: ChangePassCodeRequestBody,
    ): ApiResponse<AuthenticatedUserResult>

    @PUT(value = "post/api/Account/resetpasswordbycode")
    suspend fun resetPassCode(
        @Body body: ResetPassCodeRequestBody,
    ): ApiResponse<MessageResult>

    @PUT(value = "put/TerminalPasscode/Forgot")
    suspend fun forgotTerminalAccessPin(
        @Body body: ForgotTerminalAccessPinRequestBody,
    ): ApiResponse<StatusResult>
}
