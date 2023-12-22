package com.bankly.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bankly.core.database.model.EodTransaction

@Database(
    entities = [EodTransaction.BankTransfer::class, EodTransaction.CardTransfer::class, EodTransaction.CardPayment::class, EodTransaction.BillPayment::class, EodTransaction.PayWithTransfer::class],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getEodDao(): EodDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppRoomDatabase::class.java, "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}