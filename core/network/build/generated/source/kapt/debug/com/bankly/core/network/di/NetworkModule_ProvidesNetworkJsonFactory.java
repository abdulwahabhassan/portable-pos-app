package com.bankly.core.network.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;

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
public final class NetworkModule_ProvidesNetworkJsonFactory implements Factory<Json> {
  @Override
  public Json get() {
    return providesNetworkJson();
  }

  public static NetworkModule_ProvidesNetworkJsonFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Json providesNetworkJson() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.providesNetworkJson());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvidesNetworkJsonFactory INSTANCE = new NetworkModule_ProvidesNetworkJsonFactory();
  }
}
