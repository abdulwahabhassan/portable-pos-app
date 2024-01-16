package com.bankly.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_message")
data class Notification(
    @ColumnInfo(name = "title")
    val title: String,
    @PrimaryKey
    @ColumnInfo(name = "date_time")
    val dateTime: String,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "seen")
    val seen: Boolean = false
)