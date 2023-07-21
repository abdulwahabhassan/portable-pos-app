package com.bankly.feature.authentication.ui.login

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.State
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.util.Validator.isPhoneNumberValid
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetTokenUseCase
import com.bankly.core.model.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginTokenUseCase: GetTokenUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : BaseViewModel<LoginScreenEvent, LoginScreenState>(LoginScreenState()) {

    override suspend fun handleUiEvents(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.LoginScreen -> {
                performLogin(
                    phoneNumber = event.phoneNumber,
                    passCode = event.passCode
                )
            }

            is LoginScreenEvent.OnEnterPhoneNumber -> {
                setUiState {
                    copy(
                        phoneNumberTFV = event.phoneNumberTFV,
                        isPhoneNumberError = !isPhoneNumberValid(event.phoneNumberTFV.text),
                        phoneNumberFeedBack = if (isPhoneNumberValid(event.phoneNumberTFV.text)) "" else "Please enter a valid phone number"
                    )
                }
            }

            is LoginScreenEvent.OnEnterPassCode -> {
                setUiState {
                    copy(
                        passCodeTFV = event.passCodeTFV,
                        isPassCodeError = event.passCodeTFV.text.trim().isEmpty(),
                        passCodeFeedBack = if (event.passCodeTFV.text.trim().isEmpty()) "Please enter your passcode" else ""
                    )
                }
            }
        }
    }

    private suspend fun performLogin(phoneNumber: String, passCode: String) {
        loginTokenUseCase(phoneNumber, passCode)
            .onEach { resource ->
                resource.onLoading {
                    setUiState { copy(loginState = State.Loading, isUserInputEnabled = false) }
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
                    setUiState { copy(loginState = State.Success(data = tokenObj)) }
                }
                resource.onFailure { failureMessage ->
                    setUiState {
                        copy(
                            loginState = State.Error(message = failureMessage),
                            isUserInputEnabled = true
                        )
                    }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        loginState = State.Error(
                            message = it.localizedMessage ?: it.message
                            ?: "An unexpected event occurred!"
                        ),
                        isUserInputEnabled = true
                    )
                }
            }.launchIn(viewModelScope)
    }
}
