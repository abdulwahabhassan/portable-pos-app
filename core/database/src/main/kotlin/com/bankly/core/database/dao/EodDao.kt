package com.bankly.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bankly.core.database.model.EodTransaction

@Dao
interface EodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBankTransfer(transaction: EodTransaction.BankTransfer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardTransfer(transaction: EodTransaction.CardTransfer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardPayment(transaction: EodTransaction.CardPayment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillPayment(transaction: EodTransaction.BillPayment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayWithTransfer(transaction: EodTransaction.PayWithTransfer)

    @Query("SELECT * FROM `eod_bank_transfer` WHERE local_date BETWEEN :dateCreatedFrom AND :dateCreatedTo ORDER BY date_created DESC")
    suspend fun getAllBankTransfer(dateCreatedFrom: String, dateCreatedTo: String): List<EodTransaction.BankTransfer>

    @Query("SELECT * FROM `eod_card_transfer` WHERE local_date BETWEEN :dateCreatedFrom AND :dateCreatedTo ORDER BY date_created DESC")
    suspend fun getAllCardTransfer(dateCreatedFrom: String, dateCreatedTo: String): List<EodTransaction.CardTransfer>

    @Query("SELECT * FROM `eod_card_payment` WHERE local_date BETWEEN :dateCreatedFrom AND :dateCreatedTo ORDER BY date_time DESC")
    suspend fun getAllCardPayment(dateCreatedFrom: String, dateCreatedTo: String): List<EodTransaction.CardPayment>

    @Query("SELECT * FROM `eod_bill_payment` WHERE local_date BETWEEN :dateCreatedFrom AND :dateCreatedTo ORDER BY paid_on DESC")
    suspend fun getAllBillPayment(dateCreatedFrom: String, dateCreatedTo: String): List<EodTransaction.BillPayment>

    @Query("SELECT * FROM `eod_pay_with_transfer` WHERE local_date BETWEEN :dateCreatedFrom AND :dateCreatedTo ORDER BY date_created DESC")
    suspend fun getAllPayWithTransferPayment(dateCreatedFrom: String, dateCreatedTo: String): List<EodTransaction.PayWithTransfer>
}