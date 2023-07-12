package com.bankly.feature.authentication.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.util.Validator.validatePhoneNumber
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginTokenUseCase: GetTokenUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : BaseViewModel<LoginUiEvent, LoginState>(LoginState.Initial) {

    override suspend fun handleUiEvents(event: LoginUiEvent) {
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

    private suspend fun performLogin(phoneNumber: String, passCode: String) {
        loginTokenUseCase(phoneNumber, passCode)
            .onEach { resource ->
                resource.onLoading {
                    setUiState { LoginState.Loading }
                }
                resource.onReady { tokenObj ->
                    Log.d("debug", "token: ${tokenObj.token}")
                    userPreferencesDataStore.update {
                        copy(token = buildString {
                            append(tokenObj.tokenType)
                            append(" ")
                            append(tokenObj.token)
                        })
                    }
                    setUiState { LoginState.Success }
                }
                resource.onFailure { message ->
                    setUiState { LoginState.Error(message) }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    LoginState.Error(
                        it.message ?: "An unexpected event occurred!"
                    )
                }
            }.launchIn(viewModelScope)
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
