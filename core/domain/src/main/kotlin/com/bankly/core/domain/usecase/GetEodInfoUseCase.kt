package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.EndOfDayRepository
import com.bankly.core.model.entity.EodInfo
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEodInfoUseCase @Inject constructor(
    private val eodRepository: EndOfDayRepository,
) {
    suspend operator fun invoke(token: String): Flow<Resource<com.bankly.core.model.entity.EodInfo>> =
        eodRepository.getEodInfo(token)
}
