package com.bankly.core.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base class for all ViewModels using Unidirectional Data Flow.
 *
 * @param E the type of the Event
 * @param S the type of the State
 * @param O the type of the OneShotState
 * @param initialState The initial state of the ViewModel.
 *
 */

abstract class BaseViewModel<E, S, O: OneShotState>(initialState: S) : ViewModel() {
    /**
     * [E] stands for Event
     * [S] stands for State
     * [O] stands for OneShotState
     */
    private val event = MutableSharedFlow<E>()
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    /**
     * One shot ui state for sending one time events to the ui
     */
    private val _oneShotState = Channel<O>(Channel.BUFFERED)
    val oneShotState = _oneShotState.receiveAsFlow()

    init {
        event.onEach(::handleUiEvents).launchIn(viewModelScope)
    }

    /**
     * Handles the event and updates the state.
     * @param event The event to handle.
     */
    protected abstract suspend fun handleUiEvents(event: E)

    /**
     * Emits an event to the event flow.
     * @param event the event to emit
     */
    fun sendEvent(event: E) {
        viewModelScope.launch {
            this@BaseViewModel.event.emit(event)
        }
    }

    /**
     * Emits a state to the state flow.
     * @param transform The new state function.
     */
    protected fun setUiState(transform: S.() -> S) {
        viewModelScope.launch {
            _uiState.emit(transform(_uiState.value))
        }
    }

    /**
     * Sends one shot state to the ui
     */
    protected fun setOneShotState(oneShotState: O) {
        viewModelScope.launch {
            _oneShotState.trySend(oneShotState)
        }
    }
}

interface OneShotState