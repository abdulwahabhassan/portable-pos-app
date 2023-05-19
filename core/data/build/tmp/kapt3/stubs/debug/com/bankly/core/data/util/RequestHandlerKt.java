package com.bankly.core.data.util;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aS\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u001c\u0010\t\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\r\u001aM\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u001c\u0010\t\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0010"}, d2 = {"handleRequest", "Lcom/bankly/core/common/model/Result;", "T", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "networkMonitor", "Lcom/bankly/core/data/util/NetworkMonitor;", "json", "Lkotlinx/serialization/json/Json;", "apiRequest", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/CoroutineDispatcher;Lcom/bankly/core/data/util/NetworkMonitor;Lkotlinx/serialization/json/Json;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handleTokenRequest", "Lcom/bankly/core/network/model/response/TokenNetworkResponse;", "data_debug"})
public final class RequestHandlerKt {
    
    @org.jetbrains.annotations.Nullable
    public static final <T extends java.lang.Object>java.lang.Object handleRequest(@org.jetbrains.annotations.NotNull
    kotlinx.coroutines.CoroutineDispatcher dispatcher, @org.jetbrains.annotations.NotNull
    com.bankly.core.data.util.NetworkMonitor networkMonitor, @org.jetbrains.annotations.NotNull
    kotlinx.serialization.json.Json json, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> apiRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.bankly.core.common.model.Result<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public static final java.lang.Object handleTokenRequest(@org.jetbrains.annotations.NotNull
    kotlinx.coroutines.CoroutineDispatcher dispatcher, @org.jetbrains.annotations.NotNull
    com.bankly.core.data.util.NetworkMonitor networkMonitor, @org.jetbrains.annotations.NotNull
    kotlinx.serialization.json.Json json, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.bankly.core.network.model.response.TokenNetworkResponse>, ? extends java.lang.Object> apiRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.bankly.core.common.model.Result<com.bankly.core.network.model.response.TokenNetworkResponse>> $completion) {
        return null;
    }
}