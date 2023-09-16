package com.bankly.core.sealed

sealed interface Resource<out T> {
    data class Ready<T>(val data: T) : Resource<T>
    data class Failed(val message: String) : Resource<Nothing>
    object Loading : Resource<Nothing>
}

inline fun <T : Any> Resource<T>.onLoading(
    action: () -> Unit,
): Resource<T> {
    if (this is Resource.Loading) action()
    return this
}

inline fun <T : Any> Resource<T>.onReady(
    action: (data: T) -> Unit,
): Resource<T> {
    if (this is Resource.Ready) action(data)
    return this
}

inline fun <T : Any> Resource<T>.onFailure(
    action: (message: String) -> Unit,
): Resource<T> {
    if (this is Resource.Failed) action(message)
    return this
}
