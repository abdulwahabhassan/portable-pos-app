package com.bankly.core.data.di

import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.data.repository.DefaultUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsDashboardRepository(
        repository: DefaultUserRepository
    ): UserRepository

}