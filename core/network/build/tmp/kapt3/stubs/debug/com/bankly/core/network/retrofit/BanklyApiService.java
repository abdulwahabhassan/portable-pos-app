package com.bankly.core.network.retrofit;

/**
 * Retrofit API declarations for Bankly APIs
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\bv\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/bankly/core/network/retrofit/BanklyApiService;", "", "Base", "Notification", "Pos", "Tms", "network_debug"})
public abstract interface BanklyApiService {
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJO\u0010\f\u001a\u00020\r2\b\b\u0003\u0010\u000e\u001a\u00020\u000f2\b\b\u0003\u0010\u0010\u001a\u00020\u000f2\b\b\u0003\u0010\u0011\u001a\u00020\u000f2\b\b\u0001\u0010\u0012\u001a\u00020\u000f2\b\b\u0001\u0010\u0013\u001a\u00020\u000f2\n\b\u0003\u0010\u0014\u001a\u0004\u0018\u00010\u000fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u001aH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/bankly/core/network/retrofit/BanklyApiService$Base;", "", "changePassCode", "Lcom/bankly/core/network/model/response/NetworkResponse;", "Lcom/bankly/core/network/model/AuthenticatedUser;", "body", "Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassCode", "Lcom/bankly/core/network/model/ResultStatus;", "Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getToken", "Lcom/bankly/core/network/model/response/TokenNetworkResponse;", "clientId", "", "clientSecret", "grantType", "username", "password", "authMode", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetPassCode", "Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "validateOtp", "Lcom/bankly/core/network/model/request/ValidateOtpRequestBody;", "(Lcom/bankly/core/network/model/request/ValidateOtpRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "network_debug"})
    public static abstract interface Base {
        
        @retrofit2.http.POST(value = "identity/post/api/Account/password/v2/forgotpasswordcode")
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object forgotPassCode(@retrofit2.http.Body
        @org.jetbrains.annotations.NotNull
        com.bankly.core.network.model.request.ForgotPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.ResultStatus>> $completion);
        
        @retrofit2.http.PUT(value = "identity/post/api/Account/validateotp")
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object validateOtp(@retrofit2.http.Body
        @org.jetbrains.annotations.NotNull
        com.bankly.core.network.model.request.ValidateOtpRequestBody body, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.ResultStatus>> $completion);
        
        @retrofit2.http.FormUrlEncoded
        @retrofit2.http.POST(value = "identity/post/connect/token")
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object getToken(@retrofit2.http.Field(value = "client_id")
        @org.jetbrains.annotations.NotNull
        java.lang.String clientId, @retrofit2.http.Field(value = "client_secret")
        @org.jetbrains.annotations.NotNull
        java.lang.String clientSecret, @retrofit2.http.Field(value = "grant_type")
        @org.jetbrains.annotations.NotNull
        java.lang.String grantType, @retrofit2.http.Field(value = "username")
        @org.jetbrains.annotations.NotNull
        java.lang.String username, @retrofit2.http.Field(value = "password")
        @org.jetbrains.annotations.NotNull
        java.lang.String password, @retrofit2.http.Field(value = "auth_mode")
        @org.jetbrains.annotations.Nullable
        java.lang.String authMode, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.TokenNetworkResponse> $completion);
        
        @retrofit2.http.PUT(value = "identity/put/TerminalPasscode/Change")
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object changePassCode(@retrofit2.http.Body
        @org.jetbrains.annotations.NotNull
        com.bankly.core.network.model.request.ChangePassCodeRequestBody body, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.AuthenticatedUser>> $completion);
        
        @retrofit2.http.PUT(value = "identity/put/TerminalPasscode/Reset")
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object resetPassCode(@retrofit2.http.Body
        @org.jetbrains.annotations.NotNull
        com.bankly.core.network.model.request.ResetPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.AuthenticatedUser>> $completion);
        
        @kotlin.Metadata(mv = {1, 8, 0}, k = 3, xi = 48)
        public static final class DefaultImpls {
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\b"}, d2 = {"Lcom/bankly/core/network/retrofit/BanklyApiService$Notification;", "", "getUserWallet", "Lcom/bankly/core/network/model/response/NetworkResponse;", "Lcom/bankly/core/network/model/NetworkUserWallet;", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "network_debug"})
    public static abstract interface Notification {
        
        @retrofit2.http.GET(value = "/user-wallet")
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object getUserWallet(@retrofit2.http.Query(value = "id")
        @org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.NetworkUserWallet>> $completion);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\b"}, d2 = {"Lcom/bankly/core/network/retrofit/BanklyApiService$Pos;", "", "getUserWallet", "Lcom/bankly/core/network/model/response/NetworkResponse;", "Lcom/bankly/core/network/model/NetworkUserWallet;", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "network_debug"})
    public static abstract interface Pos {
        
        @retrofit2.http.GET(value = "/user-wallet")
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object getUserWallet(@retrofit2.http.Query(value = "id")
        @org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.NetworkUserWallet>> $completion);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\b"}, d2 = {"Lcom/bankly/core/network/retrofit/BanklyApiService$Tms;", "", "getUserWallet", "Lcom/bankly/core/network/model/response/NetworkResponse;", "Lcom/bankly/core/network/model/NetworkUserWallet;", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "network_debug"})
    public static abstract interface Tms {
        
        @retrofit2.http.GET(value = "/user-wallet")
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object getUserWallet(@retrofit2.http.Query(value = "id")
        @org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.NetworkUserWallet>> $completion);
    }
}