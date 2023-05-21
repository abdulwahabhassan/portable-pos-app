package com.bankly.feature.authentication.ui;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BA\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\tH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\tH\u00c6\u0003JE\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\tH\u00d6\u0001R\u0011\u0010\f\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011\u00a8\u0006 "}, d2 = {"Lcom/bankly/feature/authentication/ui/LoginUiState;", "", "phoneNumber", "Landroidx/compose/ui/text/input/TextFieldValue;", "passCode", "isPhoneNumberError", "", "isPassCodeError", "phoneNumberFeedBack", "", "passCodeFeedBack", "(Landroidx/compose/ui/text/input/TextFieldValue;Landroidx/compose/ui/text/input/TextFieldValue;ZZLjava/lang/String;Ljava/lang/String;)V", "isLoginButtonEnabled", "()Z", "getPassCode", "()Landroidx/compose/ui/text/input/TextFieldValue;", "getPassCodeFeedBack", "()Ljava/lang/String;", "getPhoneNumber", "getPhoneNumberFeedBack", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "authentication_debug"})
@androidx.compose.runtime.Immutable
public final class LoginUiState {
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.ui.text.input.TextFieldValue phoneNumber = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.ui.text.input.TextFieldValue passCode = null;
    private final boolean isPhoneNumberError = false;
    private final boolean isPassCodeError = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String phoneNumberFeedBack = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String passCodeFeedBack = null;
    
    public LoginUiState(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.text.input.TextFieldValue phoneNumber, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.text.input.TextFieldValue passCode, boolean isPhoneNumberError, boolean isPassCodeError, @org.jetbrains.annotations.NotNull
    java.lang.String phoneNumberFeedBack, @org.jetbrains.annotations.NotNull
    java.lang.String passCodeFeedBack) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.ui.text.input.TextFieldValue getPhoneNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.ui.text.input.TextFieldValue getPassCode() {
        return null;
    }
    
    public final boolean isPhoneNumberError() {
        return false;
    }
    
    public final boolean isPassCodeError() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPhoneNumberFeedBack() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPassCodeFeedBack() {
        return null;
    }
    
    public final boolean isLoginButtonEnabled() {
        return false;
    }
    
    public LoginUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.ui.text.input.TextFieldValue component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.ui.text.input.TextFieldValue component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.bankly.feature.authentication.ui.LoginUiState copy(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.text.input.TextFieldValue phoneNumber, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.text.input.TextFieldValue passCode, boolean isPhoneNumberError, boolean isPassCodeError, @org.jetbrains.annotations.NotNull
    java.lang.String phoneNumberFeedBack, @org.jetbrains.annotations.NotNull
    java.lang.String passCodeFeedBack) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}