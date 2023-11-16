package com.bankly.feature.authentication.ui.setuppin

import android.util.Log
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetPinViewModel @Inject constructor() :
    BaseViewModel<SetPinScreenEvent, SetPinScreenState, SetPinScreenOneShotState>(SetPinScreenState()) {
    override suspend fun handleUiEvents(event: SetPinScreenEvent) {
        when (event) {
            is SetPinScreenEvent.OnEnterNewPin -> {
                setUiState { copy(newPin = event.pin, isNewPinError = false) }
            }

            is SetPinScreenEvent.OnEnterNewPinDoneClick -> {
                setOneShotState(
                    SetPinScreenOneShotState.OnGoToConfirmPinScreen(
                        defaultPin = event.defaultPin,
                        newPin = event.newPin
                    )
                )
            }

            SetPinScreenEvent.OnDismissOnBackPressWarningDialog -> {
                setUiState {
                    copy(
                        showOnBackPressScreenWarningDialog = false,
                    )
                }
            }

            is SetPinScreenEvent.SetDefaultPin -> {
                setUiState { copy(defaultPin = event.defaultPin) }
            }

            SetPinScreenEvent.OnBackPress -> {
                setUiState {
                    copy(
                        showOnBackPressScreenWarningDialog = true,
                    )
                }
            }
        }
    }

}
