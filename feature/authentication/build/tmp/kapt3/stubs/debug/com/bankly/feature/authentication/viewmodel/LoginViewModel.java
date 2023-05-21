package com.bankly.feature.authentication.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0002H\u0014J\u0018\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/bankly/feature/authentication/viewmodel/LoginViewModel;", "Lcom/bankly/core/common/viewmodel/BaseViewModel;", "Lcom/bankly/feature/authentication/viewmodel/LoginUiEvent;", "Lcom/bankly/feature/authentication/viewmodel/LoginState;", "userRepository", "Lcom/bankly/core/data/repository/UserRepository;", "(Lcom/bankly/core/data/repository/UserRepository;)V", "handleUiEvents", "", "event", "performLogin", "phoneNumber", "", "passCode", "validatePhoneNumber", "", "authentication_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class LoginViewModel extends com.bankly.core.common.viewmodel.BaseViewModel<com.bankly.feature.authentication.viewmodel.LoginUiEvent, com.bankly.feature.authentication.viewmodel.LoginState> {
    @org.jetbrains.annotations.NotNull
    private final com.bankly.core.data.repository.UserRepository userRepository = null;
    
    @javax.inject.Inject
    public LoginViewModel(@org.jetbrains.annotations.NotNull
    com.bankly.core.data.repository.UserRepository userRepository) {
        super(null);
    }
    
    @java.lang.Override
    protected void handleUiEvents(@org.jetbrains.annotations.NotNull
    com.bankly.feature.authentication.viewmodel.LoginUiEvent event) {
    }
    
    private final boolean validatePhoneNumber(java.lang.String phoneNumber) {
        return false;
    }
    
    private final void performLogin(java.lang.String phoneNumber, java.lang.String passCode) {
    }
}