package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.BillsRepository
import com.bankly.core.entity.Provider
import com.bankly.core.enums.BillsProviderType
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillProvidersUseCase @Inject constructor(
    private val billsRepository: BillsRepository,
) {
    suspend operator fun invoke(token: String, billsProviderType: BillsProviderType): Flow<Resource<List<Provider>>> =
        when(billsProviderType) {
            BillsProviderType.AIRTIME -> billsRepository.getAirtimeProviders(token = token)
            BillsProviderType.INTERNET_DATA -> billsRepository.getInternetDataProviders(token = token)
            BillsProviderType.CABLE_TV -> billsRepository.getCableTvProviders(token = token)
            BillsProviderType.ELECTRICITY -> billsRepository.getElectricityProviders(token = token)
        }
}
