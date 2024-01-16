package com.bankly.core.data.repository

import android.util.Log
import com.bankly.core.model.data.TransactionFilterData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asEodTransaction
import com.bankly.core.data.util.asRequestParam
import com.bankly.core.data.util.asTransaction
import com.bankly.core.data.util.asTransactionFilterType
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleTransactionApiResponse
import com.bankly.core.database.dao.EodDao
import com.bankly.core.database.model.EodTransaction
import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.model.entity.Transaction
import com.bankly.core.model.entity.TransactionFilterType
import com.bankly.core.network.retrofit.service.TransactionService
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.Result
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultTransactionRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val transactionService: TransactionService,
    private val eodDao: EodDao,
) : TransactionRepository {
    override suspend fun getTransactions(
        token: String,
        minimum: Long,
        maximum: Long,
        filter: com.bankly.core.model.data.TransactionFilterData,
    ): Flow<Resource<List<com.bankly.core.model.entity.Transaction>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleTransactionApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        transactionService.getTransactions(
                            token = token,
                            minimum = minimum,
                            maximum = maximum,
                            filter = filter.asRequestParam(),
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { it.asTransaction() }))
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getEodTransactions(
        filter: com.bankly.core.model.data.TransactionFilterData,
    ): Flow<Resource<List<com.bankly.core.model.entity.Transaction>>> = flow {
        emit(Resource.Loading)
        try {
            Log.d("debug filer", "date from: ${filter.dateCreatedFrom}")
            Log.d("debug filer", "date to: ${filter.dateCreatedTo}")
            val transactions = eodDao.getAllBillPayment(filter.dateCreatedFrom, filter.dateCreatedTo).asSequence().map { it.toTransaction() }
                .plus(eodDao.getAllBankTransfer(filter.dateCreatedFrom, filter.dateCreatedTo).asSequence().map { it.toTransaction() })
                .plus(eodDao.getAllCardTransfer(filter.dateCreatedFrom, filter.dateCreatedTo).asSequence().map { it.toTransaction() })
                .plus(eodDao.getAllCardPayment(filter.dateCreatedFrom, filter.dateCreatedTo).asSequence().map { it.toTransaction() })
                .plus(eodDao.getAllPayWithTransferPayment(filter.dateCreatedFrom, filter.dateCreatedTo).asSequence().map { it.toTransaction() })
                .toList().sortedByDescending { it.transactionDate }
            Log.d("debug eod", "eod transactions: $transactions")
            emit(Resource.Ready(transactions))
        } catch (e: Exception) {
            emit(Resource.Failed(e.localizedMessage ?: ""))
        }

    }

    override suspend fun getTransactionsFilterTypes(token: String): Flow<Resource<List<TransactionFilterType>>> =
        flow {
            emit(Resource.Loading)
            when (
                val requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        transactionService.getTransactionFilterTypes(
                            token = token,
                        )
                    },
                )
            ) {
                is Result.Error -> emit(Resource.Failed(requestResult.message))
                is Result.Success -> emit(Resource.Ready(requestResult.data.map { it.asTransactionFilterType() }))
                Result.SessionExpired -> emit(Resource.SessionExpired)
            }
        }

    override suspend fun saveToEod(transactionReceipt: TransactionReceipt) {
        Log.d("debug eod", "save to eod: $transactionReceipt")
        when (transactionReceipt) {
            is TransactionReceipt.BankTransfer -> transactionReceipt.asEodTransaction()
                ?.let { eodDao.insertBankTransfer(it as EodTransaction.BankTransfer) }

            is TransactionReceipt.BillPayment -> transactionReceipt.asEodTransaction()
                ?.let { eodDao.insertBillPayment(it as EodTransaction.BillPayment) }

            is TransactionReceipt.CardPayment -> transactionReceipt.asEodTransaction()
                ?.let { eodDao.insertCardPayment(it as EodTransaction.CardPayment) }

            is TransactionReceipt.CardTransfer -> transactionReceipt.asEodTransaction()
                ?.let { eodDao.insertCardTransfer(it as EodTransaction.CardTransfer) }

            is TransactionReceipt.PayWithTransfer -> transactionReceipt.asEodTransaction()
                ?.let { eodDao.insertPayWithTransfer(it as EodTransaction.PayWithTransfer) }

            is TransactionReceipt.History -> {}
        }

    }
}
