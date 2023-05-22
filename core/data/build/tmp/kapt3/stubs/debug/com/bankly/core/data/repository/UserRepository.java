package com.bankly.core.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J%\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ%\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u000bH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ-\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00040\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J%\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0015H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J%\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0018H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/bankly/core/data/repository/UserRepository;", "", "changePassCode", "Lkotlinx/coroutines/flow/Flow;", "Lcom/bankly/core/common/model/Resource;", "Lcom/bankly/core/model/User;", "body", "Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassCode", "Lcom/bankly/core/model/Status;", "Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getToken", "Lcom/bankly/core/model/Token;", "userName", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetPassCode", "Lcom/bankly/core/model/Message;", "Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "validateOtp", "Lcom/bankly/core/network/model/request/ValidateOtpRequestBody;", "(Lcom/bankly/core/network/model/request/ValidateOtpRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public abstract interface UserRepository {
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getToken(@org.jetbrains.annotations.NotNull
    java.lang.String userName, @org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Token>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object forgotPassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ForgotPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Status>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object validateOtp(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ValidateOtpRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Status>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object resetPassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ResetPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Message>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object changePassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ChangePassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.User>>> $completion);
}