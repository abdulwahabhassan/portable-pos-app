package com.bankly.core.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Base class for all ViewModels using Unidirectional Data Flow.
 *
 * @param E the type of the Event
 * @param S the type of the State
 * @param initialState The initial state of the ViewModel.
 *
 */

abstract class BaseViewModel<E, S>(initialState: S) : ViewModel() {

    /**
     * [E] stands for Event
     * [S] stands for State
     */
    private val uiEvent = MutableSharedFlow<E>()
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    init {
        uiEvent.onEach(::handleUiEvents).launchIn(viewModelScope)
    }

    /**
     * Handles the event and updates the state.
     * @param event The event to handle.
     */
    protected abstract fun handleUiEvents(event: E)

    /**
     * Emits an event to the event flow.
     * @param event the event to emit
     */
    fun sendEvent(event: E) {
        viewModelScope.launch {
            uiEvent.emit(event)
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
}

interface OneShotUiState