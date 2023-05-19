package com.bankly.feature.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<LoginUiEvent, LoginUiState>(LoginUiState.Initial) {

    override fun handleUiEvents(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.Login -> {
                performLogin(
                    phoneNumber = event.phoneNumber,
                    passCode = event.passCode
                )
            }
        }
    }

    private fun performLogin(phoneNumber: String, passCode: String) {
        Log.d("Debug", "view model debug: $phoneNumber $passCode")
        viewModelScope.launch {
            userRepository.getToken(phoneNumber, passCode)
                .onEach { resource ->
                    resource.onLoading {
                        Log.d("Debug", "view model debug: onLoading $resource")
                        setUiState { LoginUiState.Loading }
                    }
                    resource.onReady { token ->
                        Log.d("Debug", "view model debug: onReady $resource")
                        /**
                         * TODO -> Cache **token**
                         */
                        setUiState { LoginUiState.Success }
                    }
                    resource.onFailure { message ->
                        Log.d("Debug", "view model debug: onFailure $resource")
                        setUiState { LoginUiState.Error(message) }
                    }
                }.catch {
                    Log.d("Debug", "view model debug: caught an exception ${it.message}")
                    it.printStackTrace()
                    setUiState {
                        LoginUiState.Error(
                            it.message ?: "An unexpected event occurred. We're fixing this!"
                        )
                    }
                }.collect()
        }
    }


}

sealed interface LoginUiState {
    object Initial : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}

sealed interface LoginUiEvent {
    data class Login(
        val phoneNumber: String,
        val passCode: String
    ) : LoginUiEvent
}
