package com.bankly.feature.authentication.ui.recover

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.util.Validator.validatePhoneNumber
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.repository.UserRepository
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class RecoverPassCodeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<RecoverPassCodeUiEvent, RecoverPassCodeState>(RecoverPassCodeState.Initial) {

    override fun handleUiEvents(event: RecoverPassCodeUiEvent) {
        when (event) {
            is RecoverPassCodeUiEvent.RecoverPassCode -> {
                if (validatePhoneNumber(event.phoneNumber)) {
                    recoverPassCode(
                        phoneNumber = event.phoneNumber
                    )
                } else {
                    setUiState { RecoverPassCodeState.Error("Please enter a valid phone number") }
                }
            }

            RecoverPassCodeUiEvent.ResetState -> {
                setUiState { RecoverPassCodeState.Initial }
            }

            is RecoverPassCodeUiEvent.ResetPassCode -> TODO()

        }
    }

    private fun recoverPassCode(phoneNumber: String) {
        Log.d("login debug view model", "perform logic")
        viewModelScope.launch {
            userRepository.forgotPassCode(body = ForgotPassCodeRequestBody(phoneNumber = phoneNumber))
                .onEach { resource ->
                    resource.onLoading {
                        setUiState { RecoverPassCodeState.Loading }
                    }
                    resource.onReady {
                        setUiState { RecoverPassCodeState.Success }
                    }
                    resource.onFailure { message ->
                        setUiState { RecoverPassCodeState.Error(message) }
                    }
                }.catch {
                    it.printStackTrace()
                    setUiState {
                        RecoverPassCodeState.Error(
                            it.message ?: "An unexpected event occurred. We're fixing this!"
                        )
                    }
                }.collect()
        }
    }

}

sealed interface RecoverPassCodeState {
    object Initial : RecoverPassCodeState
    object Loading : RecoverPassCodeState
    object Success : RecoverPassCodeState
    data class Error(val errorMessage: String) : RecoverPassCodeState
}

sealed interface RecoverPassCodeUiEvent {
    data class RecoverPassCode(val phoneNumber: String) : RecoverPassCodeUiEvent
    data class ResetPassCode(val phoneNumber: String) : RecoverPassCodeUiEvent
    object ResetState : RecoverPassCodeUiEvent
}
