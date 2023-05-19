package com.bankly.core.network.retrofit.datasource;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.serialization.json.Json;
import okhttp3.Call;

@ScopeMetadata("javax.inject.Singleton")
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
public final class BanklyPosRemoteDataSource_Factory implements Factory<BanklyPosRemoteDataSource> {
  private final Provider<Json> networkJsonProvider;

  private final Provider<Call.Factory> okhttpCallFactoryProvider;

  public BanklyPosRemoteDataSource_Factory(Provider<Json> networkJsonProvider,
      Provider<Call.Factory> okhttpCallFactoryProvider) {
    this.networkJsonProvider = networkJsonProvider;
    this.okhttpCallFactoryProvider = okhttpCallFactoryProvider;
  }

  @Override
  public BanklyPosRemoteDataSource get() {
    return newInstance(networkJsonProvider.get(), okhttpCallFactoryProvider.get());
  }

  public static BanklyPosRemoteDataSource_Factory create(Provider<Json> networkJsonProvider,
      Provider<Call.Factory> okhttpCallFactoryProvider) {
    return new BanklyPosRemoteDataSource_Factory(networkJsonProvider, okhttpCallFactoryProvider);
  }

  public static BanklyPosRemoteDataSource newInstance(Json networkJson,
      Call.Factory okhttpCallFactory) {
    return new BanklyPosRemoteDataSource(networkJson, okhttpCallFactory);
  }
}
