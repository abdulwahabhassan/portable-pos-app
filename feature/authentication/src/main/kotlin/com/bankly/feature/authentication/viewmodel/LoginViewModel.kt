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
) : BaseViewModel<LoginUiEvent, LoginState>(LoginState.Initial) {

    override fun handleUiEvents(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.Login -> {
                if (validatePhoneNumber(event.phoneNumber)) {
                    performLogin(
                        phoneNumber = event.phoneNumber,
                        passCode = event.passCode
                    )
                } else {
                    setUiState { LoginState.Error("Please enter a valid phone number") }
                }
            }

            LoginUiEvent.ResetState -> {
                setUiState { LoginState.Initial }
            }
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return if (phoneNumber.length == 11) return true else false
    }

    private fun performLogin(phoneNumber: String, passCode: String) {
        Log.d("login debug view model", "perform logic")
        viewModelScope.launch {
            userRepository.getToken(phoneNumber, passCode)
                .onEach { resource ->
                    resource.onLoading {
                        setUiState { LoginState.Loading }
                    }
                    resource.onReady { token ->
                        /**
                         * TODO -> Cache **token**
                         */
                        setUiState { LoginState.Success }
                    }
                    resource.onFailure { message ->
                        setUiState { LoginState.Error(message) }
                    }
                }.catch {
                    it.printStackTrace()
                    setUiState {
                        LoginState.Error(
                            it.message ?: "An unexpected event occurred. We're fixing this!"
                        )
                    }
                }.collect()
        }
    }

}

sealed interface LoginState {
    object Initial : LoginState
    object Loading : LoginState
    object Success : LoginState
    data class Error(val errorMessage: String) : LoginState
}

sealed interface LoginUiEvent {
    data class Login(
        val phoneNumber: String,
        val passCode: String
    ) : LoginUiEvent

    object ResetState : LoginUiEvent
}
