package com.example.jileme.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jileme.domain.model.DailyRecord
import com.example.jileme.data.repository.DailyRecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DailyRecordRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recordType: String = checkNotNull(savedStateHandle["type"])
    
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val recordsFlow = when (recordType) {
                "jie_se" -> repository.get戒色Records()
                "la_shi" -> repository.get拉屎Records()
                "chou_yan" -> repository.get抽烟Records()
                else -> return@launch
            }

            recordsFlow.collect { records ->
                val totalCount = when (recordType) {
                    "jie_se" -> repository.get戒色Count()
                    "la_shi" -> repository.get拉屎Count()
                    "chou_yan" -> repository.get抽烟Count()
                    else -> 0
                }

                val consecutiveDays = calculateConsecutiveDays(records)

                _uiState.update {
                    DetailUiState.Success(
                        records = records,
                        totalCount = totalCount,
                        consecutiveDays = consecutiveDays,
                        type = recordType
                    )
                }
            }
        }
    }

    private fun calculateConsecutiveDays(records: List<DailyRecord>): Int {
        if (records.isEmpty()) return 0
        
        var consecutive = 0
        var currentDate = LocalDate.now()
        
        for (record in records.take(365)) {
            val daysDiff = ChronoUnit.DAYS.between(record.date, currentDate)
            if (daysDiff <= 1) {
                consecutive++
                currentDate = record.date
            } else {
                break
            }
        }
        
        return consecutive
    }
}

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(
        val records: List<DailyRecord>,
        val totalCount: Int,
        val consecutiveDays: Int,
        val type: String
    ) : DetailUiState
}
