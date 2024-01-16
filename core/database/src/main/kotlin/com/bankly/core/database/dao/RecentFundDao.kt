package com.bankly.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bankly.core.model.entity.RecentFund
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentFundDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecentFunds(recentFunds: List<RecentFund>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentFund(recentFund: RecentFund)

    @Query("SELECT * FROM `recent_fund` ORDER BY transaction_date DESC")
    fun getRecentFunds(): Flow<List<RecentFund>>

    @Query("SELECT * FROM recent_fund WHERE transaction_reference LIKE :transactionRef AND " +
            "session_id LIKE :sessionId LIMIT 1")
    suspend fun getRecentFund(transactionRef: String, sessionId: String): RecentFund
}