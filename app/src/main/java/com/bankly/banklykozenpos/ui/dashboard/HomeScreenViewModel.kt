package com.bankly.banklykozenpos.ui.dashboard

import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.domain.usecase.GetWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase
) : BaseViewModel<HomeUiEvent, HomeState>(HomeState.Initial) {
    override fun handleUiEvents(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.GetWallet -> getWallet()
        }
    }

    private fun getWallet() {

    }

}

sealed interface HomeState {
    object Initial : HomeState
    object Loading : HomeState
    object Success : HomeState
    data class Error(val errorMessage: String) : HomeState
}

sealed interface HomeUiEvent {
    object GetWallet : HomeUiEvent
}
