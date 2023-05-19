package com.bankly.core.network.retrofit.datasource;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/bankly/core/network/retrofit/datasource/BanklyTmsRemoteDataSource;", "Lcom/bankly/core/network/RemoteDataSource$BanklyTmsDataSource;", "networkJson", "Lkotlinx/serialization/json/Json;", "okhttpCallFactory", "Lokhttp3/Call$Factory;", "(Lkotlinx/serialization/json/Json;Lokhttp3/Call$Factory;)V", "networkApi", "Lcom/bankly/core/network/retrofit/BanklyApiService$Tms;", "kotlin.jvm.PlatformType", "network_debug"})
public final class BanklyTmsRemoteDataSource implements com.bankly.core.network.RemoteDataSource.BanklyTmsDataSource {
    private final com.bankly.core.network.retrofit.BanklyApiService.Tms networkApi = null;
    
    @javax.inject.Inject
    public BanklyTmsRemoteDataSource(@org.jetbrains.annotations.NotNull
    kotlinx.serialization.json.Json networkJson, @org.jetbrains.annotations.NotNull
    okhttp3.Call.Factory okhttpCallFactory) {
        super();
    }
}