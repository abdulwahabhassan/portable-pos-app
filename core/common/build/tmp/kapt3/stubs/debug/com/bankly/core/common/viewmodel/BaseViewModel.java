package com.bankly.core.common.viewmodel;

/**
 * Base class for all ViewModels using Unidirectional Data Flow.
 *
 * @param E the type of the Event
 * @param S the type of the State
 * @param initialState The initial state of the ViewModel.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0005J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0000H$\u00a2\u0006\u0002\u0010\u0005J\u0013\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0005J!\u0010\u0012\u001a\u00020\u000f2\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00010\u0014\u00a2\u0006\u0002\b\u0015H\u0004R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0016"}, d2 = {"Lcom/bankly/core/common/viewmodel/BaseViewModel;", "E", "S", "Landroidx/lifecycle/ViewModel;", "initialState", "(Ljava/lang/Object;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "uiEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "handleUiEvents", "", "event", "sendEvent", "setUiState", "transform", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "common_debug"})
public abstract class BaseViewModel<E extends java.lang.Object, S extends java.lang.Object> extends androidx.lifecycle.ViewModel {
    
    /**
     * [E] stands for Event
     * [S] stands for State
     */
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableSharedFlow<E> uiEvent = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<S> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<S> uiState = null;
    
    public BaseViewModel(S initialState) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<S> getUiState() {
        return null;
    }
    
    /**
     * Handles the event and updates the state.
     * @param event The event to handle.
     */
    protected abstract void handleUiEvents(E event);
    
    /**
     * Emits an event to the event flow.
     * @param event the event to emit
     */
    public final void sendEvent(E event) {
    }
    
    /**
     * Emits a state to the state flow.
     * @param transform The new state function.
     */
    protected final void setUiState(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super S, ? extends S> transform) {
    }
}