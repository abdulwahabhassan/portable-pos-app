package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.BillsRepository
import com.bankly.core.entity.BillPlan
import com.bankly.core.enums.BillsPlanType
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillPlansUseCase @Inject constructor(
    private val billsRepository: BillsRepository,
) {
    suspend operator fun invoke(token: String, billPlanType: BillsPlanType, billId: Long): Flow<Resource<List<BillPlan>>> =
        when(billPlanType) {
            BillsPlanType.INTERNET_DATA -> billsRepository.getInternetDataPlans(token = token, billId)
            BillsPlanType.CABLE_TV -> billsRepository.getCableTvPlans(token = token, billId)
            BillsPlanType.ELECTRICITY -> billsRepository.getElectricityPlans(token = token, billId)
        }
}