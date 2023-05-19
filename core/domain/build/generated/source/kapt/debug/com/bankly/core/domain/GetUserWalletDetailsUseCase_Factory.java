package com.bankly.core.domain;

import com.bankly.core.data.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class GetUserWalletDetailsUseCase_Factory implements Factory<GetUserWalletDetailsUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public GetUserWalletDetailsUseCase_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public GetUserWalletDetailsUseCase get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static GetUserWalletDetailsUseCase_Factory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new GetUserWalletDetailsUseCase_Factory(userRepositoryProvider);
  }

  public static GetUserWalletDetailsUseCase newInstance(UserRepository userRepository) {
    return new GetUserWalletDetailsUseCase(userRepository);
  }
}
