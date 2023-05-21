package com.bankly.feature.authentication.viewmodel;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/bankly/feature/authentication/viewmodel/LoginUiEvent;", "", "Login", "ResetState", "Lcom/bankly/feature/authentication/viewmodel/LoginUiEvent$Login;", "Lcom/bankly/feature/authentication/viewmodel/LoginUiEvent$ResetState;", "authentication_debug"})
public abstract interface LoginUiEvent {
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/bankly/feature/authentication/viewmodel/LoginUiEvent$Login;", "Lcom/bankly/feature/authentication/viewmodel/LoginUiEvent;", "phoneNumber", "", "passCode", "(Ljava/lang/String;Ljava/lang/String;)V", "getPassCode", "()Ljava/lang/String;", "getPhoneNumber", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "authentication_debug"})
    public static final class Login implements com.bankly.feature.authentication.viewmodel.LoginUiEvent {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String phoneNumber = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String passCode = null;
        
        public Login(@org.jetbrains.annotations.NotNull
        java.lang.String phoneNumber, @org.jetbrains.annotations.NotNull
        java.lang.String passCode) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPhoneNumber() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPassCode() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.bankly.feature.authentication.viewmodel.LoginUiEvent.Login copy(@org.jetbrains.annotations.NotNull
        java.lang.String phoneNumber, @org.jetbrains.annotations.NotNull
        java.lang.String passCode) {
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
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bankly/feature/authentication/viewmodel/LoginUiEvent$ResetState;", "Lcom/bankly/feature/authentication/viewmodel/LoginUiEvent;", "()V", "authentication_debug"})
    public static final class ResetState implements com.bankly.feature.authentication.viewmodel.LoginUiEvent {
        @org.jetbrains.annotations.NotNull
        public static final com.bankly.feature.authentication.viewmodel.LoginUiEvent.ResetState INSTANCE = null;
        
        private ResetState() {
            super();
        }
    }
}