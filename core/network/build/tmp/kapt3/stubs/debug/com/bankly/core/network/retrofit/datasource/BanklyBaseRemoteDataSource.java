package com.bankly.core.network.retrofit.datasource;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u001f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u0011H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J!\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u001f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u001aH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bR\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/bankly/core/network/retrofit/datasource/BanklyBaseRemoteDataSource;", "Lcom/bankly/core/network/RemoteDataSource$BanklyBaseDataSource;", "networkJson", "Lkotlinx/serialization/json/Json;", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lkotlinx/serialization/json/Json;Lokhttp3/OkHttpClient;)V", "networkApi", "Lcom/bankly/core/network/retrofit/BanklyApiService$Base;", "kotlin.jvm.PlatformType", "changePassCode", "Lcom/bankly/core/network/model/response/NetworkResponse;", "Lcom/bankly/core/network/model/AuthenticatedUser;", "body", "Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassCode", "Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getToken", "Lcom/bankly/core/network/model/response/TokenNetworkResponse;", "userName", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetPassCode", "Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "network_debug"})
public final class BanklyBaseRemoteDataSource implements com.bankly.core.network.RemoteDataSource.BanklyBaseDataSource {
    private final com.bankly.core.network.retrofit.BanklyApiService.Base networkApi = null;
    
    @javax.inject.Inject
    public BanklyBaseRemoteDataSource(@org.jetbrains.annotations.NotNull
    kotlinx.serialization.json.Json networkJson, @org.jetbrains.annotations.NotNull
    okhttp3.OkHttpClient okHttpClient) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object resetPassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ResetPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.AuthenticatedUser>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object forgotPassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ForgotPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.AuthenticatedUser>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object changePassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ChangePassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.AuthenticatedUser>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getToken(@org.jetbrains.annotations.NotNull
    java.lang.String userName, @org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.TokenNetworkResponse> $completion) {
        return null;
    }
}