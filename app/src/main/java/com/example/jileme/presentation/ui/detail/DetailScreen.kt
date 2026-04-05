package com.example.jileme.presentation.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jileme.presentation.ui.theme.ChouYanColor
import com.example.jileme.presentation.ui.theme.JieSeColor
import com.example.jileme.presentation.ui.theme.LaShiColor
import com.example.jileme.presentation.viewmodel.DetailUiState
import com.example.jileme.presentation.viewmodel.DetailViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    type: String,
    onNavigateBack: () -> Unit = {},
    showBack: Boolean = true,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            if (showBack) {
                TopAppBar(
                    title = { Text(getTitle(type)) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = getColor(type),
                        titleContentColor = Color.White
                    )
                )
            } else {
                CenterAlignedTopAppBar(
                    title = { Text(getTitle(type)) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = getColor(type),
                        titleContentColor = Color.White
                    )
                )
            }
        }
    ) { padding ->
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DetailUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        StatsCard(
                            totalCount = state.totalCount,
                            consecutiveDays = state.consecutiveDays,
                            type = state.type
                        )
                    }

                    item {
                        Text(
                            text = "历史记录",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(state.records) { record ->
                        RecordItem(record = record, type = state.type)
                    }
                }
            }
        }
    }
}

@Composable
fun StatsCard(totalCount: Int, consecutiveDays: Int, type: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getColor(type).copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "总次数",
                value = totalCount.toString(),
                color = getColor(type)
            )
            StatItem(
                label = "连续天数",
                value = consecutiveDays.toString(),
                color = getColor(type)
            )
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun RecordItem(record: com.example.jileme.domain.model.DailyRecord, type: String) {
    val formatter = DateTimeFormatter.ofPattern("MM月dd日 EEEE", Locale.CHINESE)
    val note = when (type) {
        "jie_se" -> record.戒色备注
        "la_shi" -> record.拉屎备注
        "chou_yan" -> record.抽烟备注
        else -> null
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = record.date.format(formatter),
                style = MaterialTheme.typography.titleMedium
            )
            if (!note.isNullOrBlank()) {
                Text(
                    text = "备注: $note",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

fun getTitle(type: String): String {
    return when (type) {
        "jie_se" -> "戒了么 - 戒色"
        "la_shi" -> "拉了么 - 拉屎"
        "chou_yan" -> "抽了么 - 抽烟"
        else -> "详情"
    }
}

fun getColor(type: String): Color {
    return when (type) {
        "jie_se" -> JieSeColor
        "la_shi" -> LaShiColor
        "chou_yan" -> ChouYanColor
        else -> Color(0xFF6B7280)
    }
}
