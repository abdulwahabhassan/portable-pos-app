package com.bankly.core.network;

/**
 * Interface representing network calls to backend
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\bv\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/bankly/core/network/RemoteDataSource;", "", "BanklyBaseDataSource", "BanklyNotificationDataSource", "BanklyPosDataSource", "BanklyTmsDataSource", "network_debug"})
public abstract interface RemoteDataSource {
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\u001f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\tH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ!\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u001f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0012H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/bankly/core/network/RemoteDataSource$BanklyBaseDataSource;", "", "changePassCode", "Lcom/bankly/core/network/model/response/NetworkResponse;", "Lcom/bankly/core/network/model/AuthenticatedUser;", "body", "Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ChangePassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassCode", "Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ForgotPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getToken", "Lcom/bankly/core/network/model/response/TokenNetworkResponse;", "userName", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetPassCode", "Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;", "(Lcom/bankly/core/network/model/request/ResetPassCodeRequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "network_debug"})
    public static abstract interface BanklyBaseDataSource {
        
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object resetPassCode(@org.jetbrains.annotations.NotNull
        com.bankly.core.network.model.request.ResetPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.AuthenticatedUser>> $completion);
        
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object forgotPassCode(@org.jetbrains.annotations.NotNull
        com.bankly.core.network.model.request.ForgotPassCodeRequestBody body, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.AuthenticatedUser>> $completion);
        
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object changePassCode(@org.jetbrains.annotations.NotNull
        com.bankly.core.network.model.request.ChangePassCodeRequestBody body, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.NetworkResponse<com.bankly.core.network.model.AuthenticatedUser>> $completion);
        
        @org.jetbrains.annotations.Nullable
        public abstract java.lang.Object getToken(@org.jetbrains.annotations.NotNull
        java.lang.String userName, @org.jetbrains.annotations.NotNull
        java.lang.String password, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.TokenNetworkResponse> $completion);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lcom/bankly/core/network/RemoteDataSource$BanklyNotificationDataSource;", "", "network_debug"})
    public static abstract interface BanklyNotificationDataSource {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lcom/bankly/core/network/RemoteDataSource$BanklyPosDataSource;", "", "network_debug"})
    public static abstract interface BanklyPosDataSource {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lcom/bankly/core/network/RemoteDataSource$BanklyTmsDataSource;", "", "network_debug"})
    public static abstract interface BanklyTmsDataSource {
    }
}