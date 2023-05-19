package com.bankly.core.data.util;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\f\u0010\t\u001a\u00020\u0007*\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\b\u00a8\u0006\u000b"}, d2 = {"Lcom/bankly/core/data/util/ConnectivityManagerNetworkMonitor;", "Lcom/bankly/core/data/util/NetworkMonitor;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "isOnline", "Lkotlinx/coroutines/flow/Flow;", "", "()Lkotlinx/coroutines/flow/Flow;", "isCurrentlyConnected", "Landroid/net/ConnectivityManager;", "data_debug"})
public final class ConnectivityManagerNetworkMonitor implements com.bankly.core.data.util.NetworkMonitor {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isOnline = null;
    
    @javax.inject.Inject
    public ConnectivityManagerNetworkMonitor(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> isOnline() {
        return null;
    }
    
    private final boolean isCurrentlyConnected(android.net.ConnectivityManager $this$isCurrentlyConnected) {
        return false;
    }
}