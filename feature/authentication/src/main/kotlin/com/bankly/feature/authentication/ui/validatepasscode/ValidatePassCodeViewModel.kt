package com.bankly.feature.authentication.ui.validatepasscode

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.ValidatePassCodeUseCase
import com.bankly.core.model.entity.Token
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.onFailure
import com.bankly.core.model.sealed.onLoading
import com.bankly.core.model.sealed.onReady
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ValidatePassCodeViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val validatePassCodeUseCase: ValidatePassCodeUseCase,
) : BaseViewModel<ValidatePassCodeScreenEvent, ValidatePassCodeScreenState, ValidatePassCodeScreenOneShotState>(
    ValidatePassCodeScreenState(),
) {

    override suspend fun handleUiEvents(event: ValidatePassCodeScreenEvent) {
        when (event) {
            is ValidatePassCodeScreenEvent.OnValidatePassCode -> {
                performValidation(event.password)
            }

            is ValidatePassCodeScreenEvent.OnEnterPassCode -> {
                setUiState {
                    val isEmpty = event.passCodeTFV.text.trim().isEmpty()
                    copy(
                        passCodeTFV = event.passCodeTFV,
                        isPassCodeError = isEmpty,
                        passCodeFeedBack = if (isEmpty) "Please enter your passcode" else "",
                    )
                }
            }

            ValidatePassCodeScreenEvent.OnDismissErrorDialog -> {
                setUiState {
                    copy(showErrorDialog = false, errorDialogMessage = "")
                }
            }
        }
    }

    private suspend fun performValidation(password: String) {
        validatePassCodeUseCase.invoke(
            password,
            userPreferencesDataStore.data().token.substringAfter(" "),
        )
            .onEach { resource: Resource<com.bankly.core.model.entity.Token> ->
                Log.d("debug token", "token ${userPreferencesDataStore.data().token}")
                resource.onLoading {
                    setUiState { copy(isLoading = true) }
                }
                resource.onFailure { errorMessage: String ->
                    setUiState {
                        copy(
                            isLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = errorMessage,
                        )
                    }
                }
                resource.onReady {
                    setUiState {
                        copy(
                            isLoading = false,
                        )
                    }
                    setOneShotState(ValidatePassCodeScreenOneShotState.GoToSettingsScreen)
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        isLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: "",
                    )
                }
            }.launchIn(viewModelScope)
    }
}
