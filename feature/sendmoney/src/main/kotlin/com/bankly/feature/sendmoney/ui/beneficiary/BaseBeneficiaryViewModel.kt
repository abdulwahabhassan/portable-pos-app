package com.bankly.feature.sendmoney.ui.beneficiary

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.State
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBanksUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.model.Bank
import com.bankly.core.model.NameEnquiry
import com.bankly.feature.sendmoney.model.SendMoneyChannel
import com.bankly.feature.sendmoney.model.Type
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseBeneficiaryViewModel constructor(
    private val nameEnquiryUseCase: NameEnquiryUseCase,
    private val getBanksUseCase: GetBanksUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : BaseViewModel<BeneficiaryScreenEvent, BeneficiaryScreenState, BeneficiaryScreenOneShotState>(
    BeneficiaryScreenState()
) {
    init {
        viewModelScope.launch { getBanks() }
    }

    private suspend fun getBanks() {
        getBanksUseCase.invoke(userPreferencesDataStore.data().token)
            .onEach { resource ->
                resource.onLoading {
                    Log.d("debug getBanks", "(onLoading) ...")
                    setUiState {
                        copy(bankListState = State.Loading)
                    }
                }
                resource.onReady { banks: List<Bank> ->
                    Log.d("debug getBanks", "(onReady) banks: $banks")
                    setUiState {
                        copy(bankListState = State.Success(banks))
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug getBanks", "(onFailure) message: $message")
                    setUiState {
                        copy(bankListState = State.Error(message))
                    }
                }
            }
            .catch {
                Log.d("debug getBanks", "(error caught) message: ${it.message}")
                it.printStackTrace()
                setUiState {
                    copy(bankListState = State.Error(it.message ?: "An unexpected error occurred"))
                }
            }
            .launchIn(viewModelScope)
    }

    protected suspend fun doNameEnquiry(number: String, bankId: Long?, channel: SendMoneyChannel) {
        Log.d("debug doNameEnquiry", "doNameEnquiry called")
        when (uiState.value.type) {
            Type.ACCOUNT_NUMBER -> {
                when (channel) {
                    SendMoneyChannel.BANKLY_TO_OTHER -> {
                        validateAccountNumber(
                            accountNumber = number,
                            bankId = bankId
                        )
                    }

                    SendMoneyChannel.BANKLY_TO_BANKLY -> {
                        validatePhoneNumber(phoneNumber = number)
                    }
                }
            }

            Type.PHONE_NUMBER -> {
                validatePhoneNumber(phoneNumber = number)
            }
        }
    }

    protected suspend fun validateAccountNumber(
        accountNumber: String,
        bankId: Long?
    ) {
        Log.d("debug validateAccountNumber", "account number: $accountNumber, bank id: $bankId")
        if (bankId != null && accountNumber.isNotEmpty()) {
            nameEnquiryUseCase.performNameEnquiry(
                userPreferencesDataStore.data().token,
                accountNumber,
                bankId.toString()
            ).onEach { resource ->
                resource.onLoading {
                    Log.d("debug getBanks", "(onLoading) ...")
                    setUiState {
                        copy(accountOrPhoneValidationState = State.Loading)
                    }
                }
                resource.onReady { nameEnquiry: NameEnquiry ->
                    Log.d("debug getBanks", "(onReady) banks: $nameEnquiry")
                    setUiState {
                        copy(accountOrPhoneValidationState = State.Success(nameEnquiry), accountOrPhoneFeedBack = nameEnquiry.accountName)
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug getBanks", "(onFailure) message: $message")
                    setUiState {
                        copy(accountOrPhoneValidationState = State.Error(message), accountOrPhoneFeedBack = message, isAccountOrPhoneError = true)
                    }
                }
            }.catch {
                Log.d("debug getBanks", "(error caught) message: ${it.message}")
                it.printStackTrace()
                setUiState {
                    copy(
                        accountOrPhoneValidationState = State.Error(
                            it.message ?: "An unexpected error occurred"
                        )
                    )
                }
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun validatePhoneNumber(phoneNumber: String) {
        Log.d("debug validatePhoneNumber", "phoneNumber: $phoneNumber")
        nameEnquiryUseCase.performNameEnquiry(
            userPreferencesDataStore.data().token,
            phoneNumber
        ).onEach { resource ->
            resource.onLoading {
                Log.d("debug getBanks", "(onLoading) ...")
                setUiState {
                    copy(accountOrPhoneValidationState = State.Loading)
                }
            }
            resource.onReady { nameEnquiry: NameEnquiry ->
                Log.d("debug getBanks", "(onReady) banks: $nameEnquiry")
                setUiState {
                    copy(accountOrPhoneValidationState = State.Success(nameEnquiry), accountOrPhoneFeedBack = nameEnquiry.accountName)
                }
            }
            resource.onFailure { message ->
                Log.d("debug getBanks", "(onFailure) message: $message")
                setUiState {
                    copy(accountOrPhoneValidationState = State.Error(message), accountOrPhoneFeedBack = message, isAccountOrPhoneError = true)
                }
            }
        }.catch {
            Log.d("debug getBanks", "(error caught) message: ${it.message}")
            it.printStackTrace()
            setUiState {
                copy(
                    accountOrPhoneValidationState = State.Error(
                        it.message ?: "An unexpected error occurred"
                    )
                )
            }
        }.launchIn(viewModelScope)
    }
}