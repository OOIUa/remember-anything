package com.example.jileme.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jileme.data.repository.DailyRecordRepository
import com.example.jileme.domain.model.DailyRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HabitCalendarViewModel @Inject constructor(
    private val repository: DailyRecordRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val habitType: String = checkNotNull(savedStateHandle["type"])

    private val visibleMonth = MutableStateFlow(YearMonth.now())
    private val selectedDate = MutableStateFlow(LocalDate.now())

    val uiState: StateFlow<HabitCalendarUiState> = combine(
        repository.getAllRecords(),
        visibleMonth,
        selectedDate
    ) { allRecords, month, selected ->
        val start = month.atDay(1)
        val end = month.atEndOfMonth()
        val inMonth = allRecords.filter { !it.date.isBefore(start) && !it.date.isAfter(end) }
        val recordsByDate = inMonth.associateBy { it.date }
        val recordForSelected = recordsByDate[selected] ?: DailyRecord(date = selected)
        val monthDoneCount = inMonth.count { isDoneForHabit(it, habitType) }
        HabitCalendarUiState.Success(
            habitType = habitType,
            visibleMonth = month,
            recordsByDate = recordsByDate,
            selectedDate = selected,
            selectedRecord = recordForSelected,
            monthDoneCount = monthDoneCount
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HabitCalendarUiState.Loading
    )

    fun selectDate(date: LocalDate) {
        visibleMonth.value = YearMonth.from(date)
        selectedDate.value = date
    }

    fun previousMonth() {
        val m = visibleMonth.value.minusMonths(1)
        visibleMonth.value = m
        val day = selectedDate.value.dayOfMonth.coerceAtMost(m.lengthOfMonth())
        selectedDate.value = m.atDay(day)
    }

    fun nextMonth() {
        val m = visibleMonth.value.plusMonths(1)
        visibleMonth.value = m
        val day = selectedDate.value.dayOfMonth.coerceAtMost(m.lengthOfMonth())
        selectedDate.value = m.atDay(day)
    }

    fun updateRecord(record: DailyRecord) {
        viewModelScope.launch {
            if (record.id == 0L) {
                repository.insertRecord(record)
            } else {
                repository.updateRecord(record)
            }
        }
    }
}

private fun isDoneForHabit(record: DailyRecord, habitType: String): Boolean {
    return when (habitType) {
        "jie_se" -> record.戒色
        "la_shi" -> record.拉屎
        "chou_yan" -> record.抽烟
        else -> false
    }
}

sealed interface HabitCalendarUiState {
    data object Loading : HabitCalendarUiState
    data class Success(
        val habitType: String,
        val visibleMonth: YearMonth,
        val recordsByDate: Map<LocalDate, DailyRecord>,
        val selectedDate: LocalDate,
        val selectedRecord: DailyRecord,
        val monthDoneCount: Int
    ) : HabitCalendarUiState
}
