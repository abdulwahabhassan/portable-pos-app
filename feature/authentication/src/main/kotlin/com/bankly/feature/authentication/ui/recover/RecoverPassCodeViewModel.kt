package com.bankly.feature.authentication.ui.recover

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.util.Validator.validatePhoneNumber
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.domain.usecase.ForgotPassCodeUseCase
import com.bankly.core.model.ForgotPassCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class RecoverPassCodeViewModel @Inject constructor(
    private val forgotPassCodeUseCase: ForgotPassCodeUseCase
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

        }
    }

    private fun recoverPassCode(phoneNumber: String) {
        viewModelScope.launch {
            forgotPassCodeUseCase(body = ForgotPassCode(phoneNumber = phoneNumber))
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
    object ResetState : RecoverPassCodeUiEvent
}
