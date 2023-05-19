package com.bankly.core.common.decoder;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class UriDecoder_Factory implements Factory<UriDecoder> {
  @Override
  public UriDecoder get() {
    return newInstance();
  }

  public static UriDecoder_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static UriDecoder newInstance() {
    return new UriDecoder();
  }

  private static final class InstanceHolder {
    private static final UriDecoder_Factory INSTANCE = new UriDecoder_Factory();
  }
}
