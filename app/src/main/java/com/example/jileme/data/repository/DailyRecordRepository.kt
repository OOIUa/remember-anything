package com.example.jileme.data.repository

import com.example.jileme.data.local.DailyRecordDao
import com.example.jileme.data.local.DailyRecordEntity
import com.example.jileme.data.mapper.toDomain
import com.example.jileme.data.mapper.toEntity
import com.example.jileme.domain.model.DailyRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyRecordRepository @Inject constructor(
    private val dao: DailyRecordDao
) {
    fun getAllRecords(): Flow<List<DailyRecord>> {
        return dao.getAllRecords().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getRecordByDate(date: LocalDate): DailyRecord? {
        val dateString = date.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)
        return dao.getRecordByDate(dateString)?.toDomain()
    }

    fun getRecentRecords(limit: Int): Flow<List<DailyRecord>> {
        return dao.getRecentRecords(limit).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun insertRecord(record: DailyRecord): Long {
        return dao.insertRecord(record.toEntity())
    }

    suspend fun updateRecord(record: DailyRecord) {
        dao.updateRecord(record.toEntity())
    }

    suspend fun deleteRecord(record: DailyRecord) {
        dao.deleteRecord(record.toEntity())
    }

    suspend fun get戒色Count(): Int = dao.get戒色Count()
    suspend fun get拉屎Count(): Int = dao.get拉屎Count()
    suspend fun get抽烟Count(): Int = dao.get抽烟Count()

    fun get戒色Records(): Flow<List<DailyRecord>> {
        return dao.get戒色Records().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun get拉屎Records(): Flow<List<DailyRecord>> {
        return dao.get拉屎Records().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun get抽烟Records(): Flow<List<DailyRecord>> {
        return dao.get抽烟Records().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
