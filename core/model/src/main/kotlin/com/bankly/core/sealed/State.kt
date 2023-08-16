package com.bankly.core.sealed

sealed interface State<out T> {
    object Initial : State<Nothing>
    object Loading : State<Nothing>
    data class Success<T>(val data: T) : State<T>
    data class Error(val message: String) : State<Nothing>
}