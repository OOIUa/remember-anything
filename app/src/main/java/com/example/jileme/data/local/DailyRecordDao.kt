package com.example.jileme.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecordDao {
    @Query("SELECT * FROM daily_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<DailyRecordEntity>>

    @Query("SELECT * FROM daily_records WHERE date = :date LIMIT 1")
    suspend fun getRecordByDate(date: String): DailyRecordEntity?

    @Query("SELECT * FROM daily_records ORDER BY date DESC LIMIT :limit")
    fun getRecentRecords(limit: Int): Flow<List<DailyRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: DailyRecordEntity): Long

    @Update
    suspend fun updateRecord(record: DailyRecordEntity)

    @Delete
    suspend fun deleteRecord(record: DailyRecordEntity)

    @Query("SELECT COUNT(*) FROM daily_records WHERE 戒色 = 1")
    suspend fun get戒色Count(): Int

    @Query("SELECT COUNT(*) FROM daily_records WHERE 拉屎 = 1")
    suspend fun get拉屎Count(): Int

    @Query("SELECT COUNT(*) FROM daily_records WHERE 抽烟 = 1")
    suspend fun get抽烟Count(): Int

    @Query("SELECT * FROM daily_records WHERE 戒色 = 1 ORDER BY date DESC")
    fun get戒色Records(): Flow<List<DailyRecordEntity>>

    @Query("SELECT * FROM daily_records WHERE 拉屎 = 1 ORDER BY date DESC")
    fun get拉屎Records(): Flow<List<DailyRecordEntity>>

    @Query("SELECT * FROM daily_records WHERE 抽烟 = 1 ORDER BY date DESC")
    fun get抽烟Records(): Flow<List<DailyRecordEntity>>
}
