package com.bankly.core.database.di

import android.content.Context
import com.bankly.core.database.AppRoomDatabase
import com.bankly.core.database.EodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext appContext: Context
    ): AppRoomDatabase {
        return AppRoomDatabase.getDatabase(appContext)
    }

    @Provides
    @Singleton
    fun provideTransactionsDao(appDatabase: AppRoomDatabase): EodDao {
        return appDatabase.getEodDao()
    }

}
