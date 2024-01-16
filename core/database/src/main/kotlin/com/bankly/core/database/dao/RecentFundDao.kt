package com.bankly.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.bankly.core.database.model.LocalRecentFund

@Dao
interface RecentFundDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecentFunds(localRecentFunds: List<LocalRecentFund>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentFund(localRecentFund: LocalRecentFund)

    @Query("SELECT * FROM `recent_fund` ORDER BY transaction_date DESC")
    fun getRecentFunds(): Flow<List<LocalRecentFund>>

    @Query(
        "SELECT * FROM recent_fund WHERE transaction_reference LIKE :transactionRef AND " +
                "session_id LIKE :sessionId LIMIT 1"
    )
    suspend fun getRecentFund(
        transactionRef: String,
        sessionId: String,
    ): LocalRecentFund?
}