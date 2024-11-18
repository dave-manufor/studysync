package com.example.studysyncapp.presentation.agenda_screen

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studysyncapp.presentation.BodyText
import com.example.studysyncapp.presentation.Heading1
import com.example.studysyncapp.presentation.Heading2
import com.example.studysyncapp.presentation.Heading4
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.DarkBlue
import com.example.studysyncapp.ui.theme.Neutral
import com.example.studysyncapp.ui.theme.OffWhite
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.formatToCalendarDay
import com.example.studysyncapp.utils.formatToMonthString
import com.example.studysyncapp.utils.formatToYearString
import com.example.studysyncapp.utils.getDayOfWeek3Letters
import com.example.studysyncapp.utils.getDayOfWeekInt
import com.example.studysyncapp.utils.getDaysInMonth
import com.example.studysyncapp.utils.getFullWeeksForMonth
import com.example.studysyncapp.utils.getNextMonth
import com.example.studysyncapp.utils.getPrevMonth
import com.example.studysyncapp.utils.isInSameMonth
import java.util.Date

@Composable
fun AgendaScreen(agendaViewModel: AgendaViewModel = viewModel()){
    val context = LocalContext.current
    val state by agendaViewModel.state.collectAsState()
    var month by remember { mutableStateOf(Date()) }
    var selectedDate by remember { mutableStateOf(Date()) }

    fun resetCalendar(){
        month = Date()
        selectedDate = Date()
    }

    Log.d("MYTAG", "AgendaScreen Month: $month")
    Log.d("MYTAG", "AgendaScreen Selected Date: $selectedDate")

    if(state.isLoading){
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }
    if(!state.error.isNullOrEmpty()){
        Log.e("MYTAG", "Assignments Screen: ${state.error}")
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    Column(modifier = Modifier.padding(top = 0.dp, start = UiVariables.ScreenPadding, end = UiVariables.ScreenPadding, bottom = UiVariables.ScreenPadding)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = {resetCalendar()}) {
                Heading4(text = "Today")
            }
            TextButton(onClick = {agendaViewModel.onOpenCreateEvent()},) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Event",
                    tint = AccentBlue,
                    modifier = Modifier.size(24.dp)
                )
            }
            if(state.showCreateEventDialog){
                CreateEventDialog(onDismiss = {agendaViewModel.onDismissCreateEvent()}, onConfirm = {name, type, description, startsAt, endsAt -> agendaViewModel.onConfirmCreateEvent(name, type, description, startsAt, endsAt)})
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
        AgendaCalendar(month = month, selectedDate = selectedDate, onSelectDate = {date ->  selectedDate = date}, onNextMonth = {month = month.getNextMonth()}, onPrevMonth = {month = month.getPrevMonth()})
        Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
    }

}

@Composable
private fun AgendaCalendar(month: Date,
                    selectedDate: Date,
                   onSelectDate: (Date) -> Unit,
                   onNextMonth: () -> Unit,
                   onPrevMonth: () -> Unit,
                   startFromSunday: Boolean = true){
    val dates = month.getFullWeeksForMonth()
    Column(modifier = Modifier.fillMaxWidth()){
//        Header
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, contentDescription = "Previous Month", modifier = Modifier.size(28.dp).clickable { onPrevMonth() })
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Heading2(text = month.formatToMonthString())
                BodyText(text = month.formatToYearString())
            }
            Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = "Next Month", modifier = Modifier.size(28.dp).clickable { onNextMonth() })
        }

//        Calendar
        CalendarGrid(
            date = dates,
            onClick = onSelectDate,
            startFromSunday = startFromSunday,
            activeDate = selectedDate,
            activeMonth = month,
        )
    }
}

@Composable
private fun CalendarCell(
    date: Date,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val text = date.formatToCalendarDay()
    Box(
        modifier = modifier
            .size(32.dp)
            .background(
                shape = RoundedCornerShape(CornerSize(10.dp)),
                color = if(active) AccentBlue else Color.Transparent
            )
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .clickable { onClick() }
            .alpha(if (enabled) 1f else 0.3f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if(active) Color.White else DarkBlue,
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
private fun WeekdayCell(weekday: Int, modifier: Modifier = Modifier) {
    val text = weekday.getDayOfWeek3Letters()
    Box(
        modifier = modifier.size(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.orEmpty(),
            color = Neutral,
            modifier = Modifier.align(Alignment.BottomCenter),
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF8F9BB3),
            )

        )
    }
}

private fun getWeekDays(startFromSunday: Boolean): List<Int> {
    val lista = (1..7).toList()
    return (if (startFromSunday) lista else lista.drop(1) + lista.take(1)).toList()
}

@Composable
private fun CalendarGrid(
    date: List<Date>,
    activeDate: Date,
    activeMonth: Date,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val weekdays = getWeekDays(startFromSunday)
    CalendarCustomLayout(modifier = modifier.wrapContentHeight()) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }
        date.forEach {
            CalendarCell(date = it, active = it == activeDate, onClick = { onClick(it) }, enabled = it.isInSameMonth(activeMonth))
        }
    }
}

@Composable
private fun CalendarCustomLayout(
    modifier: Modifier = Modifier,
    itemSizeDp: Dp = 32.dp,
    content: @Composable () -> Unit,
) {
    val itemSize = with(LocalDensity.current) {
        itemSizeDp.roundToPx()
    }
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidthWithoutItems = constraints.maxWidth - (itemSize * 7)
        val singleGap = totalWidthWithoutItems / 6


        val xPos: MutableList<Int> = mutableListOf()
        val yPos: MutableList<Int> = mutableListOf()
        var currentX = 0
        var currentY = 0
        measurables.forEach { _ ->
            xPos.add(currentX)
            yPos.add(currentY)
            if (currentX + itemSize + singleGap > constraints.maxWidth) {
                currentX = 0
                currentY += itemSize + singleGap
            } else {
                currentX += itemSize + singleGap
            }
        }

        val placeables: List<Placeable> = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxHeight = itemSize, maxWidth = itemSize))
        }

        layout(
            width = constraints.maxWidth,
            height = currentY - singleGap,
        ) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = xPos[index],
                    y = yPos[index],
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgendaScreenPreview(){
    AgendaScreen()
}