package com.bankly.core.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J%\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ%\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\nH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ-\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J%\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0013H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2 = {"Lcom/bankly/core/data/repository/UserRepository;", "", "changePassCode", "Lkotlinx/coroutines/flow/Flow;", "Lcom/bankly/core/common/model/Resource;", "Lcom/bankly/core/model/User;", "body", "Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassCode", "Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getToken", "Lcom/bankly/core/model/Token;", "userName", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetPassCode", "Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public abstract interface UserRepository {
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object resetPassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ResetPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.User>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object forgotPassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ForgotPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.User>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object changePassCode(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.request.ChangePassCodeRequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.User>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getToken(@org.jetbrains.annotations.NotNull
    java.lang.String userName, @org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.bankly.core.common.model.Resource<com.bankly.core.model.Token>>> $completion);
}