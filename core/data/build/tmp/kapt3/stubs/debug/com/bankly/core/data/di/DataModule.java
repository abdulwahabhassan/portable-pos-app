package com.bankly.core.data.di;

@dagger.Module
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\'\u00a8\u0006\n"}, d2 = {"Lcom/bankly/core/data/di/DataModule;", "", "bindsDashboardRepository", "Lcom/bankly/core/domain/repository/UserRepository;", "repository", "Lcom/bankly/core/data/repository/DefaultUserRepository;", "bindsNetworkMonitor", "Lcom/bankly/core/data/util/NetworkMonitor;", "networkMonitor", "Lcom/bankly/core/data/util/ConnectivityManagerNetworkMonitor;", "data_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract interface DataModule {
    
    @dagger.Binds
    @org.jetbrains.annotations.NotNull
    public abstract com.bankly.core.domain.repository.UserRepository bindsDashboardRepository(@org.jetbrains.annotations.NotNull
    com.bankly.core.data.repository.DefaultUserRepository repository);
    
    @dagger.Binds
    @org.jetbrains.annotations.NotNull
    public abstract com.bankly.core.data.util.NetworkMonitor bindsNetworkMonitor(@org.jetbrains.annotations.NotNull
    com.bankly.core.data.util.ConnectivityManagerNetworkMonitor networkMonitor);
}