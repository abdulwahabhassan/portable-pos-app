package com.bankly.core.database.di

import android.content.Context
import com.bankly.core.database.AppRoomDatabase
import com.bankly.core.database.dao.EodDao
import com.bankly.core.database.dao.NotificationMessageDao
import com.bankly.core.database.dao.RecentFundDao
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
        @ApplicationContext appContext: Context,
    ): AppRoomDatabase {
        return AppRoomDatabase.getDatabase(appContext)
    }

    @Provides
    @Singleton
    fun provideTransactionsDao(appDatabase: AppRoomDatabase): EodDao {
        return appDatabase.getEodDao()
    }

    @Provides
    @Singleton
    fun provideNotificationMessageDao(appDatabase: AppRoomDatabase): NotificationMessageDao {
        return appDatabase.getNotificationMessageDao()
    }

    @Provides
    @Singleton
    fun provideRecentFundDao(appDatabase: AppRoomDatabase): RecentFundDao {
        return appDatabase.getRecentFundDao()
    }
}
