package com.bankly.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bankly.core.database.model.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotificationMessage(notificationMessage: Notification)

    @Query("SELECT * FROM notification_message ORDER BY date_time DESC")
    fun getNotificationMessages(): Flow<List<Notification>>

    @Query("SELECT * FROM notification_message WHERE date_time LIKE :dateTime LIMIT 1")
    suspend fun getNotificationMessage(dateTime: String): Notification?
}