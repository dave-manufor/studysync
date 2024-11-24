package com.example.studysyncapp.presentation.classroom.list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studysyncapp.R
import com.example.studysyncapp.presentation.utils.ConfirmationDialog
import com.example.studysyncapp.presentation.utils.ConfirmationDialogType
import com.example.studysyncapp.presentation.utils.ElevatedCard
import com.example.studysyncapp.presentation.utils.ElevatedCardText
import com.example.studysyncapp.presentation.utils.ElevatedCardTitle
import com.example.studysyncapp.presentation.utils.Heading1
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.Neutral
import com.example.studysyncapp.ui.theme.NeutralLight
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.copyTextThenToast
import com.example.studysyncapp.utils.toColor

@Composable
fun ClassroomsScreen(onNavigateToClassroom: (String) -> Unit, classroomsViewModel: ClassroomsViewModel = viewModel()){
    val context = LocalContext.current
    val state by classroomsViewModel.state.collectAsState()
    if(state.isLoading){
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }
    if(!state.error.isNullOrEmpty()){
        Log.e("MYTAG", "Assignments Screen: ${state.error}")
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    Column(modifier = Modifier.padding(UiVariables.ScreenPadding)){
        ClassroomHeader(onNavigateToCreateClassroom = {classroomsViewModel.onOpenCreateClassroom()}, onNavigateToJoinClassroom = {classroomsViewModel.onOpenJoinClassroom()})
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ){
            items(state.classrooms){
                ClassroomCard(
                    id = it.id,
                    title = it.name,
                    description = it.description ?: "",
                    accentColor = it.color.toColor(),
                    onNavigateToClassroom = {id -> onNavigateToClassroom(id)},
                    code = it.code ?: "",
                    onLeaveClassroom = {code -> classroomsViewModel.onConfirmLeaveClassroom(code)}
                )
            }
        }

//        Create Classroom Dialog
        if(state.showCreateClassroomDialog){
            CreateClassroomDialog(onDismiss = {classroomsViewModel.onDismissCreateClassroom()}, onConfirm = {name, colorString, description -> classroomsViewModel.onConfirmCreateClassroom(name, colorString, description)})
        }

//        Join Classroom Dialog
        if(state.showJoinClassroomDialog){
            JoinClassroomDialog(onDismiss = {classroomsViewModel.onDismissJoinClassroom()}, onConfirm = {classCode -> classroomsViewModel.onConfirmJoinClassroom(classCode)})
        }
    }
}

@Composable
fun ClassroomHeader(onNavigateToCreateClassroom: () -> Unit, onNavigateToJoinClassroom: () -> Unit){
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Heading1(text = "Classrooms")
            TextButton(onClick = {expanded = true},) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Classrooms Dropdown",
                    tint = AccentBlue,
                    modifier = Modifier.size(32.dp)
                )
                if(expanded){
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(text = { Text(text = "Create Classroom") }, onClick = {
                            expanded = false
                            onNavigateToCreateClassroom()
                        })
                        DropdownMenuItem(text = { Text(text = "Join Classroom") }, onClick = {
                            expanded = false
                            onNavigateToJoinClassroom()
                        })
                    }
                }
            }
        }
        HorizontalDivider(thickness = 1.dp, color = NeutralLight)
    }
}

@Composable
fun ClassroomCard(id: String, code: String, title: String, description: String, accentColor: Color, onNavigateToClassroom: (String) -> Unit, onLeaveClassroom: (code: String) -> Unit){
    val context = LocalContext.current
    var cardMenuExpanded by remember { mutableStateOf(false) }
    var showLeaveConfirmationDialog by remember { mutableStateOf(false) }
    ElevatedCard(onClick = {onNavigateToClassroom(id)}) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_classroom),
                    contentDescription = "Agenda Item Icon",
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
                ElevatedCardTitle(text = title)
            }
            Box(modifier = Modifier.size(24.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_ellipses),
                    contentDescription = "Classroom drop down",
                    tint = Neutral,
                    modifier = Modifier.size(24.dp).clickable {
                        cardMenuExpanded = true
                    })
                if (cardMenuExpanded) {
                    DropdownMenu(
                        expanded = cardMenuExpanded,
                        onDismissRequest = { cardMenuExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text(text = "Copy Classroom Code") },
                            onClick = {
                                cardMenuExpanded = false
                                copyTextThenToast(context, code)
                            })
                        DropdownMenuItem(text = { Text(text = "Leave Classroom") }, onClick = {
                            cardMenuExpanded = false
                            showLeaveConfirmationDialog = true
                        })
                    }
                }
            }
        }
        ElevatedCardText(text = description, modifier = Modifier.fillMaxWidth())
//        Leave Classroom Dialog
        if(showLeaveConfirmationDialog){
            ConfirmationDialog(
                title = "Leave Classroom",
                body = "Are you sure you want to leave this classroom? This action is permanent and cannot be undone.",
                confirmButtonText = "Leave",
                cancelButtonText = "Cancel",
                type = ConfirmationDialogType.DESTRUCTIVE,
                onDismiss = { showLeaveConfirmationDialog = false },
                onConfirm = {
                    showLeaveConfirmationDialog = false
                    onLeaveClassroom(code)
                }
            )
        }
    }
}

@Preview
@Composable
fun ClassroomCardPreview(){
    ClassroomCard(
        id= "",
        title ="ICS 3A",
        description = "Test decription for ICS 3A",
        onNavigateToClassroom = {},
        onLeaveClassroom = { },
        accentColor = AccentBlue,
        code = "Test Code",
    )
}