package com.example.jileme.presentation.ui.habit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jileme.domain.model.DailyRecord
import com.example.jileme.presentation.ui.detail.getColor
import com.example.jileme.presentation.ui.detail.getTitle
import com.example.jileme.presentation.ui.nav.GlassBarScrollTailPadding
import com.example.jileme.presentation.viewmodel.HabitCalendarUiState
import com.example.jileme.presentation.viewmodel.HabitCalendarViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitCalendarScreen(
    habitType: String,
    viewModel: HabitCalendarViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val barColor = getColor(habitType)
    val scheme = MaterialTheme.colorScheme

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = getTitle(habitType),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = barColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        val gradient = Brush.verticalGradient(
            colors = listOf(
                barColor.copy(alpha = 0.06f),
                scheme.background,
                scheme.background
            )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(gradient)
        ) {
            when (val state = uiState) {
                is HabitCalendarUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = barColor,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                is HabitCalendarUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp)
                            .padding(top = 8.dp, bottom = 8.dp)
                    ) {
                        MonthStatPill(
                            monthDoneCount = state.monthDoneCount,
                            accent = barColor
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            color = scheme.surface,
                            tonalElevation = 1.dp,
                            shadowElevation = 2.dp
                        ) {
                            MonthHeader(
                                month = state.visibleMonth,
                                onPrevious = viewModel::previousMonth,
                                onNext = viewModel::nextMonth,
                                accentColor = barColor
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large,
                            color = scheme.surface,
                            tonalElevation = 1.dp,
                            shadowElevation = 6.dp
                        ) {
                            Column(Modifier.padding(14.dp)) {
                                CalendarMonthGridSingleHabit(
                                    visibleMonth = state.visibleMonth,
                                    recordsByDate = state.recordsByDate,
                                    selectedDate = state.selectedDate,
                                    habitType = state.habitType,
                                    habitColor = barColor,
                                    onSelectDate = viewModel::selectDate
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(22.dp))
                        Text(
                            text = state.selectedDate.format(
                                DateTimeFormatter.ofPattern("yyyy年M月d日 EEEE", Locale.CHINESE)
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = scheme.onSurface,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        SelectedDaySingleHabitCard(
                            habitType = state.habitType,
                            record = state.selectedRecord,
                            accentColor = barColor,
                            onUpdate = viewModel::updateRecord
                        )
                    Spacer(modifier = Modifier.height(GlassBarScrollTailPadding))
                }
            }
        }
    }
}
}

@Composable
private fun MonthStatPill(monthDoneCount: Int, accent: Color) {
    val scheme = MaterialTheme.colorScheme
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = accent.copy(alpha = 0.12f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "本月已记录",
                style = MaterialTheme.typography.labelLarge,
                color = scheme.onSurfaceVariant
            )
            Text(
                text = "$monthDoneCount 天",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = accent
            )
        }
    }
}

@Composable
private fun MonthHeader(
    month: YearMonth,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    accentColor: Color
) {
    val scheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onPrevious,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = accentColor
            )
        ) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                contentDescription = "上一月"
            )
        }
        Text(
            text = month.format(DateTimeFormatter.ofPattern("yyyy年M月", Locale.CHINESE)),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = scheme.onSurface
        )
        IconButton(
            onClick = onNext,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = accentColor
            )
        ) {
            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = "下一月"
            )
        }
    }
}

private data class CalendarCell(val date: LocalDate, val inCurrentMonth: Boolean)

private fun buildCalendarCells(month: YearMonth): List<CalendarCell> {
    val first = month.atDay(1)
    val padStart = first.dayOfWeek.value % 7
    val daysInMonth = month.lengthOfMonth()
    val prevMonth = month.minusMonths(1)
    val lastDayPrev = prevMonth.lengthOfMonth()
    val cells = mutableListOf<CalendarCell>()
    for (i in 0 until padStart) {
        val day = lastDayPrev - padStart + i + 1
        cells.add(CalendarCell(prevMonth.atDay(day), inCurrentMonth = false))
    }
    for (d in 1..daysInMonth) {
        cells.add(CalendarCell(month.atDay(d), inCurrentMonth = true))
    }
    val padEnd = (7 - cells.size % 7) % 7
    val nextMonth = month.plusMonths(1)
    for (i in 1..padEnd) {
        cells.add(CalendarCell(nextMonth.atDay(i), inCurrentMonth = false))
    }
    return cells
}

private fun habitDone(record: DailyRecord?, habitType: String, date: LocalDate): Boolean {
    val r = record ?: DailyRecord(date = date)
    return when (habitType) {
        "jie_se" -> r.戒色
        "la_shi" -> r.拉屎
        "chou_yan" -> r.抽烟
        else -> false
    }
}

@Composable
private fun CalendarMonthGridSingleHabit(
    visibleMonth: YearMonth,
    recordsByDate: Map<LocalDate, DailyRecord>,
    selectedDate: LocalDate,
    habitType: String,
    habitColor: Color,
    onSelectDate: (LocalDate) -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val weekLabels = listOf("日", "一", "二", "三", "四", "五", "六")
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        weekLabels.forEach { label ->
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = scheme.onSurfaceVariant.copy(alpha = 0.85f),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))

    val cells = buildCalendarCells(visibleMonth)
    Column(modifier = Modifier.fillMaxWidth()) {
        cells.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                week.forEach { cell ->
                    SingleHabitDayCell(
                        cell = cell,
                        done = habitDone(recordsByDate[cell.date], habitType, cell.date),
                        isSelected = cell.date == selectedDate,
                        habitColor = habitColor,
                        onClick = { onSelectDate(cell.date) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun SingleHabitDayCell(
    cell: CalendarCell,
    done: Boolean,
    isSelected: Boolean,
    habitColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    val today = LocalDate.now()
    val isToday = cell.date == today
    val shape = RoundedCornerShape(14.dp)
    val baseBg = when {
        done && cell.inCurrentMonth -> habitColor.copy(alpha = 0.12f)
        cell.inCurrentMonth -> scheme.surfaceVariant.copy(alpha = 0.28f)
        else -> scheme.surfaceVariant.copy(alpha = 0.14f)
    }
    val ringColor = when {
        isSelected -> habitColor
        isToday -> scheme.tertiary
        else -> Color.Transparent
    }
    val ringWidth = if (isSelected) 2.dp else if (isToday) 1.5.dp else 0.dp
    val showRing = isSelected || isToday

    Column(
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape)
            .then(
                if (showRing) Modifier.border(ringWidth, ringColor, shape)
                else Modifier
            )
            .background(baseBg, shape)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = cell.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
            color = if (cell.inCurrentMonth) {
                scheme.onSurface
            } else {
                scheme.onSurfaceVariant.copy(alpha = 0.45f)
            }
        )
        Box(
            modifier = Modifier
                .size(if (done) 11.dp else 9.dp)
                .clip(CircleShape)
                .background(
                    if (done) habitColor
                    else habitColor.copy(alpha = 0.22f)
                )
        )
    }
}

private data class DayHabitCardModel(
    val title: String,
    val subtitle: String,
    val isDone: Boolean,
    val onToggle: () -> Unit
)

@Composable
private fun SelectedDaySingleHabitCard(
    habitType: String,
    record: DailyRecord,
    accentColor: Color,
    onUpdate: (DailyRecord) -> Unit
) {
    val model = when (habitType) {
        "jie_se" -> DayHabitCardModel(
            title = "戒了么",
            subtitle = "戒色",
            isDone = record.戒色,
            onToggle = { onUpdate(record.copy(戒色 = !record.戒色)) }
        )
        "la_shi" -> DayHabitCardModel(
            title = "拉了么",
            subtitle = "拉屎",
            isDone = record.拉屎,
            onToggle = { onUpdate(record.copy(拉屎 = !record.拉屎)) }
        )
        "chou_yan" -> DayHabitCardModel(
            title = "抽了么",
            subtitle = "抽烟",
            isDone = record.抽烟,
            onToggle = { onUpdate(record.copy(抽烟 = !record.抽烟)) }
        )
        else -> DayHabitCardModel("记录", "", false, {})
    }

    val scheme = MaterialTheme.colorScheme
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = scheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(accentColor, accentColor.copy(alpha = 0.55f))
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "当日记录",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = scheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                HabitRecordCard(
                    title = model.title,
                    subtitle = model.subtitle,
                    isDone = model.isDone,
                    onClick = model.onToggle,
                    color = accentColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HabitRecordCard(
    title: String,
    subtitle: String,
    isDone: Boolean,
    onClick: () -> Unit,
    color: Color
) {
    val scheme = MaterialTheme.colorScheme
    val shape = MaterialTheme.shapes.medium
    Card(
        onClick = onClick,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = if (isDone) color.copy(alpha = 0.16f) else scheme.surfaceVariant.copy(alpha = 0.35f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = if (!isDone) BorderStroke(1.dp, scheme.outlineVariant) else null,
        modifier = Modifier.size(width = 152.dp, height = 132.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (isDone) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                contentDescription = null,
                tint = if (isDone) color else scheme.outline,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = scheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
