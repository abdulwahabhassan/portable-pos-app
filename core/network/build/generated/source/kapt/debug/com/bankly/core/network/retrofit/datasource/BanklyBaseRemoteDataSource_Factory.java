package com.bankly.core.network.retrofit.datasource;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;

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
public final class BanklyBaseRemoteDataSource_Factory implements Factory<BanklyBaseRemoteDataSource> {
  private final Provider<Json> networkJsonProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public BanklyBaseRemoteDataSource_Factory(Provider<Json> networkJsonProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    this.networkJsonProvider = networkJsonProvider;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public BanklyBaseRemoteDataSource get() {
    return newInstance(networkJsonProvider.get(), okHttpClientProvider.get());
  }

  public static BanklyBaseRemoteDataSource_Factory create(Provider<Json> networkJsonProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    return new BanklyBaseRemoteDataSource_Factory(networkJsonProvider, okHttpClientProvider);
  }

  public static BanklyBaseRemoteDataSource newInstance(Json networkJson,
      OkHttpClient okHttpClient) {
    return new BanklyBaseRemoteDataSource(networkJson, okHttpClient);
  }
}
