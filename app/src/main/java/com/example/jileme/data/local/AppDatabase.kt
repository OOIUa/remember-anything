package com.example.jileme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DailyRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyRecordDao(): DailyRecordDao
}
