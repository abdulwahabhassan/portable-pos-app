package com.bankly.core.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ%\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J%\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\f2\u0006\u0010\u000f\u001a\u00020\u0014H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J-\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\r0\f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ%\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\r0\f2\u0006\u0010\u000f\u001a\u00020\u001eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ%\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\f2\u0006\u0010\u000f\u001a\u00020!H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2 = {"Lcom/bankly/core/data/repository/DefaultUserRepository;", "Lcom/bankly/core/data/repository/UserRepository;", "ioDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "networkMonitor", "Lcom/bankly/core/data/util/NetworkMonitor;", "json", "Lkotlinx/serialization/json/Json;", "banklyBaseRemoteDataSource", "Lcom/bankly/core/network/retrofit/datasource/BanklyBaseRemoteDataSource;", "(Lkotlinx/coroutines/CoroutineDispatcher;Lcom/bankly/core/data/util/NetworkMonitor;Lkotlinx/serialization/json/Json;Lcom/bankly/core/network/retrofit/datasource/BanklyBaseRemoteDataSource;)V", "changePassCode", "Lkotlinx/coroutines/flow/Flow;", "Lcom/bankly/core/common/model/Resource;", "Lcom/bankly/core/model/User;", "body", "Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassCode", "Lcom/bankly/core/model/Status;", "Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getToken", "Lcom/bankly/core/model/Token;", "userName", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetPassCode", "Lcom/bankly/core/model/Message;", "Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "validateOtp", "Lcom/bankly/core/network/model/request/ValidateOtpRequestBody;", "(Lcom/bankly/core/network/model/request/ValidateOtpRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public final class DefaultUserRepository implements com.bankly.core.data.repository.UserRepository {
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineDispatcher ioDispatcher = null;
    @org.jetbrains.annotations.NotNull
    private final com.bankly.core.data.util.NetworkMonitor networkMonitor = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.serialization.json.Json json = null;
    @org.jetbrains.annotations.NotNull
    private final com.bankly.core.network.retrofit.datasource.BanklyBaseRemoteDataSource banklyBaseRemoteDataSource = null;
    
    @javax.inject.Inject
    public DefaultUserRepository(@org.jetbrains.annotations.NotNull
    kotlinx.coroutines.CoroutineDispatcher ioDispatcher, @org.jetbrains.annotations.NotNull
    com.bankly.core.data.util.NetworkMonitor networkMonitor, @org.jetbrains.annotations.NotNull
    kotlinx.serialization.json.Json json, @org.jetbrains.annotations.NotNull
    com.bankly.core.network.retrofit.datasource.BanklyBaseRemoteDataSource banklyBaseRemoteDataSource) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getToken(@org.jetbrains.annotations.NotNull
    java.lang.String userName, @org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Token>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object forgotPassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ForgotPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Status>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object validateOtp(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ValidateOtpRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Status>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object resetPassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ResetPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Message>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object changePassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ChangePassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.User>>> $completion) {
        return null;
    }
}