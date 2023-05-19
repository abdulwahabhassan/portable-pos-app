package com.bankly.core.network.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
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
public final class NetworkModule_OkHttpClientFactory implements Factory<OkHttpClient> {
  @Override
  public OkHttpClient get() {
    return okHttpClient();
  }

  public static NetworkModule_OkHttpClientFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OkHttpClient okHttpClient() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.okHttpClient());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_OkHttpClientFactory INSTANCE = new NetworkModule_OkHttpClientFactory();
  }
}
