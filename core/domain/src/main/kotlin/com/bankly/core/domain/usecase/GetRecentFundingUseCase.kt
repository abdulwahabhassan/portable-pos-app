package com.bankly.core.domain.usecase

import com.bankly.core.model.data.GetRecentFundingData
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.model.entity.RecentFund
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.onFailure
import com.bankly.core.model.sealed.onLoading
import com.bankly.core.model.sealed.onReady
import com.bankly.core.model.sealed.onSessionExpired
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

class GetRecentFundingUseCase @Inject constructor(
    private val payWithTransferRepository: PayWithTransferRepository,
) {
    suspend operator fun invoke(
        token: String,
        body: GetRecentFundingData,
    ): Flow<Resource<List<RecentFund>>> = channelFlow {
        combine(
            payWithTransferRepository.getRemoteRecentFunds(token, body),
            payWithTransferRepository.getLocalRecentFunds()
        ) { remoteRecentFunds, localRecentFunds ->
            trySend(Resource.Ready(localRecentFunds))
            remoteRecentFunds.onLoading {
                trySend(Resource.Loading)
            }
            remoteRecentFunds.onReady { list: List<RecentFund> ->
                list.forEach {
                    payWithTransferRepository.insertRecentFund(it)
                }
            }
            remoteRecentFunds.onFailure { message: String ->
                trySend(Resource.Failed(message))
            }
            remoteRecentFunds.onSessionExpired {
                trySend(Resource.SessionExpired)
            }
        }.launchIn(this)
        awaitClose()
    }
}
