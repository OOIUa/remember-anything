package com.example.jileme.domain.model

import java.time.LocalDate

data class DailyRecord(
    val id: Long = 0,
    val date: LocalDate = LocalDate.now(),
    val 戒色: Boolean = false,
    val 拉屎: Boolean = false,
    val 抽烟: Boolean = false,
    val 戒色备注: String? = null,
    val 拉屎备注: String? = null,
    val 抽烟备注: String? = null,
    val 创建时间: Long = System.currentTimeMillis()
)
