package com.bankly.core.network.retrofit

import com.bankly.core.network.BuildConfig.CLIENT_ID
import com.bankly.core.network.BuildConfig.CLIENT_SECRET
import com.bankly.core.network.BuildConfig.GRANT_TYPE
import com.bankly.core.network.model.AuthenticatedUser
import com.bankly.core.network.model.WalletResult
import com.bankly.core.network.model.ResultMessage
import com.bankly.core.network.model.ResultStatus
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.response.TokenNetworkResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * Retrofit API declarations for Bankly APIs
 */
sealed interface BanklyApiService {

    interface Identity {
        @POST(value = "post/api/Account/password/v2/forgotpasswordcode")
        suspend fun forgotPassCode(
            @Body body: ForgotPassCodeRequestBody,
        ): NetworkResponse<ResultStatus>

        @PUT(value = "identity/post/api/Account/validateotp")
        suspend fun validateOtp(
            @Body body: ValidateOtpRequestBody,
        ): NetworkResponse<ResultStatus>

        @FormUrlEncoded
        @POST(value = "post/connect/token")
        suspend fun getToken(
            @Field("client_id") clientId: String = CLIENT_ID,
            @Field("client_secret") clientSecret: String = CLIENT_SECRET,
            @Field("grant_type") grantType: String = GRANT_TYPE,
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("auth_mode") authMode: String? = null,
        ): TokenNetworkResponse

        @PUT(value = "put/TerminalPasscode/Change")
        suspend fun changePassCode(
            @Body body: ChangePassCodeRequestBody,
        ): NetworkResponse<AuthenticatedUser>

        @PUT(value = "post/api/Account/resetpasswordbycode")
        suspend fun resetPassCode(
            @Body body: ResetPassCodeRequestBody,
        ): NetworkResponse<ResultMessage>
    }

    interface Wallet {
        @GET(value = "get/account/agent/default")
        suspend fun getWallet(
            @Header("Authorization") token: String,
        ): NetworkResponse<WalletResult>
    }

    interface Pos {
        @GET(value = "/user-wallet")
        suspend fun getUserWallet(
            @Query("id") id: String,
        ): NetworkResponse<WalletResult>

    }

    interface Notification {
        @GET(value = "/user-wallet")
        suspend fun getUserWallet(
            @Query("id") id: String,
        ): NetworkResponse<WalletResult>

    }

    interface Tms {
        @GET(value = "/user-wallet")
        suspend fun getUserWallet(
            @Query("id") id: String,
        ): NetworkResponse<WalletResult>

    }
}

