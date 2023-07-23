package com.bankly.feature.authentication.ui.recoverpasscode

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.State
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.util.Validator.isPhoneNumberValid
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.domain.usecase.ForgotPassCodeUseCase
import com.bankly.core.model.ForgotPassCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class RecoverPassCodeViewModel @Inject constructor(
    private val forgotPassCodeUseCase: ForgotPassCodeUseCase
) : BaseViewModel<RecoverPassCodeScreenEvent, RecoverPassCodeScreenState>(RecoverPassCodeScreenState()) {

    override suspend fun handleUiEvents(event: RecoverPassCodeScreenEvent) {
        when (event) {
            is RecoverPassCodeScreenEvent.OnSendCodeClick -> {
                recoverPassCode(
                    phoneNumber = event.phoneNumber
                )
            }

            is RecoverPassCodeScreenEvent.OnEnterPhoneNumber -> {
                setUiState {
                    copy(
                        phoneNumberTFV = event.phoneNumberTFV,
                        isPhoneNumberError = !isPhoneNumberValid(event.phoneNumberTFV.text),
                        phoneNumberFeedBack = if (isPhoneNumberValid(event.phoneNumberTFV.text)) "" else "Please enter a valid phone number"
                    )
                }
            }
        }
    }

    private suspend fun recoverPassCode(phoneNumber: String) {
        forgotPassCodeUseCase(body = ForgotPassCode(phoneNumber = phoneNumber))
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(recoverPassCodeState = State.Loading) }
                }
                resource.onReady { status ->
                    setUiState { copy(recoverPassCodeState = State.Success(data = status)) }
                }
                resource.onFailure { message ->
                    setUiState { copy(recoverPassCodeState = State.Error(message = message)) }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy()
                }
            }.launchIn(viewModelScope)
    }
}