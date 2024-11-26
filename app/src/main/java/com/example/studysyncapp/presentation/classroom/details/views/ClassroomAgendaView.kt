package com.example.studysyncapp.presentation.classroom.details.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.studysyncapp.R
import com.example.studysyncapp.domain.model.Schedule
import com.example.studysyncapp.domain.model.event.Event
import com.example.studysyncapp.presentation.agenda.AgendaType
import com.example.studysyncapp.presentation.agenda.toAgendaItem
import com.example.studysyncapp.presentation.utils.ElevatedCard
import com.example.studysyncapp.presentation.utils.ElevatedCardLink
import com.example.studysyncapp.presentation.utils.ElevatedCardText
import com.example.studysyncapp.presentation.utils.ElevatedCardTitle
import com.example.studysyncapp.presentation.utils.Heading3
import com.example.studysyncapp.ui.theme.NeutralLight
import com.example.studysyncapp.utils.capitalizeWords
import java.util.Date

@Composable
fun ClassroomAgendaView(schedules: List<Schedule>, events: List<Event>){
    val scrollState = rememberScrollState()
    val now = Date()
    val agendaItems = (events.map { it.toAgendaItem() } + schedules.map { it.toAgendaItem() }).sortedBy { it.sortDate }
    val upcomingAgenda = agendaItems.filter { it.sortDate == now || it.sortDate.after(now) }
    val pastAgenda = agendaItems.filter { it.sortDate.before(now) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxHeight(1f).verticalScroll(scrollState)){
        upcomingAgenda.forEach {
            ElevatedCard {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(painter = painterResource(when(it.type){
                            AgendaType.EVENT -> R.drawable.ic_calendar
                            AgendaType.TEST -> R.drawable.ic_exclaim_single
                            AgendaType.EXAM -> R.drawable.ic_exclaim_double
                            AgendaType.ASSIGNMENT -> R.drawable.ic_tasks
                            AgendaType.SCHEDULE -> R.drawable.ic_courses
                        }), contentDescription = "Agenda Item Icon", tint = it.tint, modifier = Modifier.size(24.dp))
                        ElevatedCardText(text = it.type.toString().capitalizeWords())
                    }
                    ElevatedCardText(text = it.timeString)
                }
                ElevatedCardTitle(text = it.title)
                if(it.subText.isNotEmpty()){
                    when(it.isLink){
                        true -> ElevatedCardLink(text = "Join In", uri = it.subText)
                        false -> ElevatedCardText(text = it.subText)
                    }
                }
            }
        }
        if(pastAgenda.isNotEmpty()){
            Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,) {
                Heading3("Past Agenda")
                HorizontalDivider(thickness = 1.dp, color = NeutralLight)
            }
            Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
            pastAgenda.forEach {
                ElevatedCard {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(painter = painterResource(when(it.type){
                                AgendaType.EVENT -> R.drawable.ic_calendar
                                AgendaType.TEST -> R.drawable.ic_exclaim_single
                                AgendaType.EXAM -> R.drawable.ic_exclaim_double
                                AgendaType.ASSIGNMENT -> R.drawable.ic_tasks
                                AgendaType.SCHEDULE -> R.drawable.ic_courses
                            }), contentDescription = "Agenda Item Icon", tint = it.tint, modifier = Modifier.size(24.dp))
                            ElevatedCardText(text = it.type.toString().capitalizeWords())
                        }
                        ElevatedCardText(text = it.timeString)
                    }
                    ElevatedCardTitle(text = it.title)
                    if(it.subText.isNotEmpty()){
                        when(it.isLink){
                            true -> ElevatedCardLink(text = "Join In", uri = it.subText)
                            false -> ElevatedCardText(text = it.subText)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
    }
}