package com.bankly.core.data.di

import com.bankly.core.data.repository.DefaultBillsRepository
import com.bankly.core.data.repository.DefaultEndOfDayRepository
import com.bankly.core.data.repository.DefaultNetworkCheckerRepository
import com.bankly.core.data.repository.DefaultPayWithTransferRepository
import com.bankly.core.data.repository.DefaultPushNotificationRepository
import com.bankly.core.data.repository.DefaultTransactionRepository
import com.bankly.core.data.repository.DefaultTransferRepository
import com.bankly.core.data.repository.DefaultUserRepository
import com.bankly.core.domain.repository.BillsRepository
import com.bankly.core.domain.repository.EndOfDayRepository
import com.bankly.core.domain.repository.NetworkCheckerRepository
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.domain.repository.PushNotificationRepository
import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsDashboardRepository(
        repository: DefaultUserRepository,
    ): UserRepository

    @Binds
    fun bindsTransferRepository(
        repository: DefaultTransferRepository,
    ): TransferRepository

    @Binds
    fun bindsBillsRepository(
        repository: DefaultBillsRepository,
    ): BillsRepository

    @Binds
    fun bindsPayWithTransferRepository(
        repository: DefaultPayWithTransferRepository,
    ): PayWithTransferRepository

    @Binds
    fun bindsTransactionsRepository(
        repository: DefaultTransactionRepository,
    ): TransactionRepository

    @Binds
    fun bindsNetworkCheckerRepository(
        repository: DefaultNetworkCheckerRepository,
    ): NetworkCheckerRepository

    @Binds
    fun bindsEndOfDayRepository(
        repository: DefaultEndOfDayRepository,
    ): EndOfDayRepository

    @Binds
    fun bindsPushNotificationRepository(
        repository: DefaultPushNotificationRepository,
    ): PushNotificationRepository
}
