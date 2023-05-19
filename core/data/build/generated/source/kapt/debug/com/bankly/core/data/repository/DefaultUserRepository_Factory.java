package com.bankly.core.data.repository;

import com.bankly.core.data.util.NetworkMonitor;
import com.bankly.core.network.retrofit.datasource.BanklyBaseRemoteDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.serialization.json.Json;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DefaultUserRepository_Factory implements Factory<DefaultUserRepository> {
  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<Json> jsonProvider;

  private final Provider<BanklyBaseRemoteDataSource> banklyBaseRemoteDataSourceProvider;

  public DefaultUserRepository_Factory(Provider<CoroutineDispatcher> ioDispatcherProvider,
      Provider<NetworkMonitor> networkMonitorProvider, Provider<Json> jsonProvider,
      Provider<BanklyBaseRemoteDataSource> banklyBaseRemoteDataSourceProvider) {
    this.ioDispatcherProvider = ioDispatcherProvider;
    this.networkMonitorProvider = networkMonitorProvider;
    this.jsonProvider = jsonProvider;
    this.banklyBaseRemoteDataSourceProvider = banklyBaseRemoteDataSourceProvider;
  }

  @Override
  public DefaultUserRepository get() {
    return newInstance(ioDispatcherProvider.get(), networkMonitorProvider.get(), jsonProvider.get(), banklyBaseRemoteDataSourceProvider.get());
  }

  public static DefaultUserRepository_Factory create(
      Provider<CoroutineDispatcher> ioDispatcherProvider,
      Provider<NetworkMonitor> networkMonitorProvider, Provider<Json> jsonProvider,
      Provider<BanklyBaseRemoteDataSource> banklyBaseRemoteDataSourceProvider) {
    return new DefaultUserRepository_Factory(ioDispatcherProvider, networkMonitorProvider, jsonProvider, banklyBaseRemoteDataSourceProvider);
  }

  public static DefaultUserRepository newInstance(CoroutineDispatcher ioDispatcher,
      NetworkMonitor networkMonitor, Json json,
      BanklyBaseRemoteDataSource banklyBaseRemoteDataSource) {
    return new DefaultUserRepository(ioDispatcher, networkMonitor, json, banklyBaseRemoteDataSource);
  }
}
