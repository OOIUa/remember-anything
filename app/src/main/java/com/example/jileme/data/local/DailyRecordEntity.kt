package com.example.jileme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "daily_records")
data class DailyRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // ISO format: YYYY-MM-DD
    val 戒色: Boolean = false,
    val 拉屎: Boolean = false,
    val 抽烟: Boolean = false,
    val 戒色备注: String? = null,
    val 拉屎备注: String? = null,
    val 抽烟备注: String? = null,
    val 创建时间: Long = System.currentTimeMillis()
)
