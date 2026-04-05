package com.example.jileme.data.mapper

import com.example.jileme.data.local.DailyRecordEntity
import com.example.jileme.domain.model.DailyRecord
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DailyRecordMapper {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun toDomain(entity: DailyRecordEntity): DailyRecord {
        return DailyRecord(
            id = entity.id,
            date = LocalDate.parse(entity.date, dateFormatter),
            戒色 = entity.戒色,
            拉屎 = entity.拉屎,
            抽烟 = entity.抽烟,
            戒色备注 = entity.戒色备注,
            拉屎备注 = entity.拉屎备注,
            抽烟备注 = entity.抽烟备注,
            创建时间 = entity.创建时间
        )
    }

    fun toEntity(domain: DailyRecord): DailyRecordEntity {
        return DailyRecordEntity(
            id = domain.id,
            date = domain.date.format(dateFormatter),
            戒色 = domain.戒色,
            拉屎 = domain.拉屎,
            抽烟 = domain.抽烟,
            戒色备注 = domain.戒色备注,
            拉屎备注 = domain.拉屎备注,
            抽烟备注 = domain.抽烟备注,
            创建时间 = domain.创建时间
        )
    }
}

fun DailyRecordEntity.toDomain(): DailyRecord = DailyRecordMapper.toDomain(this)

fun DailyRecord.toEntity(): DailyRecordEntity = DailyRecordMapper.toEntity(this)
