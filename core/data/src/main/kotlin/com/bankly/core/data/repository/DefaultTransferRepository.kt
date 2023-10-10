package com.bankly.core.data.repository

import com.bankly.core.data.BankTransferData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asBank
import com.bankly.core.data.util.asBankTransfer
import com.bankly.core.data.util.asNameEnquiry
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleResponse
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.entity.AccountNameEnquiry
import com.bankly.core.entity.Bank
import com.bankly.core.network.model.result.BankResult
import com.bankly.core.network.retrofit.service.AgentService
import com.bankly.core.network.retrofit.service.FundTransferService
import com.bankly.core.network.retrofit.service.TransferService
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Result
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultTransferRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val agentService: AgentService,
    private val transferService: TransferService,
    private val fundTransferService: FundTransferService,
) : TransferRepository {
    override suspend fun performTransferToAccountNumber(
        token: String,
        body: BankTransferData,
    ): Flow<Resource<TransactionReceipt.BankTransfer>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        fundTransferService.processTransferToAccountNumber(
                            token = token,
                            body = body.asRequestBody(),
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asBankTransfer()))
        }
    }

    override suspend fun performPhoneNumberTransfer(
        token: String,
        body: BankTransferData,
    ): Flow<Resource<TransactionReceipt.BankTransfer>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        transferService.processTransferToPhoneNumber(
                            token = token,
                            body = body.asRequestBody(),
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asBankTransfer()))
        }
    }

    override suspend fun performBankAccountNameEnquiry(
        token: String,
        accountNumber: String,
        bankId: String,
    ): Flow<Resource<AccountNameEnquiry>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        fundTransferService.performBankAccountNameEnquiry(
                            token = token,
                            accountNumber = accountNumber,
                            bankId = bankId,
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asNameEnquiry()))
        }
    }

    override suspend fun performBankAccountNameEnquiry(
        token: String,
        phoneNumber: String,
    ): Flow<Resource<AccountNameEnquiry>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        agentService.performAccountNameEnquiry(
                            token = token,
                            phoneNumber = phoneNumber,
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asNameEnquiry()))
        }
    }

    override suspend fun getBanks(
        token: String
    ): Flow<Resource<List<Bank>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { fundTransferService.getBanks(token = token) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { bankResult: BankResult -> bankResult.asBank() }))
        }
    }
}
