package com.bankly.core.data.di

import com.bankly.core.data.repository.UserRepository
import com.bankly.core.data.repository.DefaultUserRepository
import com.bankly.core.data.util.ConnectivityManagerNetworkMonitor
import com.bankly.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsDashboardRepository(
        repository: DefaultUserRepository
    ): UserRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}