package com.example.studysyncapp.presentation.classroom.details

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studysyncapp.R
import com.example.studysyncapp.domain.model.file.FileType
import com.example.studysyncapp.data.remote.FilesApi
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.file.File
import com.example.studysyncapp.presentation.agenda.CreateEventDialog
import com.example.studysyncapp.presentation.assignments.CreateAssignmentDialog
import com.example.studysyncapp.presentation.classroom.details.views.ClassroomAgendaView
import com.example.studysyncapp.presentation.classroom.details.views.ClassroomAssignmentsView
import com.example.studysyncapp.presentation.classroom.details.views.ClassroomCoursesView
import com.example.studysyncapp.presentation.classroom.details.views.ClassroomFilesView
import com.example.studysyncapp.presentation.courses.CreateCourseDialog
import com.example.studysyncapp.presentation.courses.CreateScheduleDialog
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.presentation.utils.FormFilePicker
import com.example.studysyncapp.presentation.utils.Heading4
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.Neutral
import com.example.studysyncapp.ui.theme.NeutralDark
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ClassroomDetailsScreen(id: String, onNavigateToClassroomsList: () -> Unit){
    val classroomDetailsViewModel: ClassroomDetailsViewModel = viewModel(factory = ClassroomDetailsViewModelFactory(id))
    val uriHandler = LocalUriHandler.current
    val state by classroomDetailsViewModel.state.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {
        TabNavList.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    Column(modifier = Modifier.fillMaxSize().padding(UiVariables.ScreenPadding)) {
        ClassroomDetailsHeader(
            classroomName = state.classroom?.name ?: "",
            isEditable = state.isEditable,
            onNavigateToClassroomsList = onNavigateToClassroomsList,
            onNavigateToUploadFile = { classroomDetailsViewModel.onOpenUploadFile() },
            isAssignmentEnabled = state.courses.isNotEmpty(),
            isScheduleEnabled = state.courses.isNotEmpty(),
            isFilesEnabled = state.courses.isNotEmpty() or state.assignments.isNotEmpty(),
            onNavigateToCreateAssignment = { classroomDetailsViewModel.onOpenCreateAssignment() },
            onNavigateToCreateCourse = { classroomDetailsViewModel.onOpenCreateCourse() },
            onNavigateToCreateSchedule = { classroomDetailsViewModel.onOpenCreateSchedule() },
            onNavigateToCreateEvent = { classroomDetailsViewModel.onOpenCreateEvent() }
        )
//        Dialogs ///////////////////////////////////////////////////////////////////////////////
        Log.d("MYTAG", "ClassroomDetailsScreen: $state")
        if(state.showUploadFileDialog){
            UploadFileDialog(
                onDismiss = { classroomDetailsViewModel.onDismissUploadFile() },
                onConfirm = { isAssignment: Boolean, course: Course?, assignment: Assignment?, file: File ->
                    classroomDetailsViewModel.onConfirmUploadFile(
                        isAssignment,
                        course,
                        assignment,
                        file
                    )
                },
                classroomId = id,
                courses = state.courses,
                assignments = state.assignments
            )
        }
        if(state.showCreateAssignmentDialog){
            CreateAssignmentDialog(
                onDismiss = { classroomDetailsViewModel.onDismissCreateAssignment() },
                onConfirm = { assignmentName: String, courseId: String, assignmentDescription: String, dueAt: String ->
                    classroomDetailsViewModel.onConfirmCreateAssignment(
                        assignmentName = assignmentName,
                        assignmentDescription = assignmentDescription,
                        courseId = courseId,
                        dueAt = dueAt,
                        classroomId = id
                    )
                },
                courses = state.courses
            )
        }
        if(state.showCreateCourseDialog){
            CreateCourseDialog(
                onDismiss = { classroomDetailsViewModel.onDismissCreateCourse() },
                onConfirm = { courseName: String, color: String, description -> classroomDetailsViewModel.onConfirmCreateCourse(courseName, color, description, id)}
            )
        }
        if(state.showCreateScheduleDialog){
            CreateScheduleDialog(
                courses = state.courses,
                onDismiss = { classroomDetailsViewModel.onDismissCreateSchedule() },
                onConfirm = { course, dayOfTheWeek, startTime, endTime, online, location ->  classroomDetailsViewModel.onConfirmCreateSchedule(
                    course = course,
                    day = dayOfTheWeek,
                    startTime = startTime,
                    endTime = endTime,
                    online = online,
                    location = location,
                    classroomId = id
                )}
            )
        }
        if(state.showCreateEventDialog){
            CreateEventDialog(
                onDismiss = { classroomDetailsViewModel.onDismissCreateEvent() },
                onConfirm = {name, type, description, startsAt, endsAt ->  classroomDetailsViewModel.onConfirmCreateEvent(
                    eventName = name,
                    eventType = type,
                    eventDescription = description,
                    eventStartDate = startsAt,
                    eventEndDate = endsAt,
                    classroomId = id
                )}
            )
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
//        Tab Navigation
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                divider = {},
                indicator = {
                    tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = AccentBlue
                        )
                },
                edgePadding = 0.dp,) {
                TabNavList.forEachIndexed { index, tabItem ->
                    val isSelected = selectedTabIndex == index
                    Tab(selected = isSelected, onClick = {selectedTabIndex = index},
                        selectedContentColor = AccentBlue,
                        unselectedContentColor = NeutralDark,
                        modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(
                                painter = painterResource(if (isSelected) tabItem.selectedIcon else tabItem.unselectedIcon),
                                contentDescription = tabItem.title,
                                tint = if (isSelected) AccentBlue else Neutral,
                                modifier = Modifier.size(20.dp)
                            )
                            Heading4(text = tabItem.title, color = if (isSelected) AccentBlue else Neutral)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
//        Active Component
        HorizontalPager(state = pagerState, pageSpacing = UiVariables.ScreenPadding, modifier = Modifier.fillMaxSize().weight(1f)){
            index ->
           when(index){
               0 -> ClassroomAgendaView(schedules = state.schedules, events = state.events)
               1 -> ClassroomCoursesView(courses = state.courses)
               2 -> ClassroomAssignmentsView(assignments = state.assignments)
               3 -> ClassroomFilesView(files = state.files, onFileClick = {file -> classroomDetailsViewModel.onFileClick(file, uriHandler)})
           }
        }
    }
}

@Composable
fun ClassroomDetailsHeader(
    classroomName: String,
    isEditable: Boolean,
    isAssignmentEnabled: Boolean = false,
    isScheduleEnabled: Boolean = false,
    isFilesEnabled: Boolean = false,
    onNavigateToClassroomsList: () -> Unit,
    onNavigateToUploadFile: () -> Unit,
    onNavigateToCreateAssignment: () -> Unit,
    onNavigateToCreateCourse: () -> Unit,
    onNavigateToCreateSchedule: () -> Unit,
    onNavigateToCreateEvent: () -> Unit
    ){
    var openMenu by remember { mutableStateOf(false) }
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Box(modifier = Modifier.clickable(indication = null, interactionSource = null) { onNavigateToClassroomsList() }){
            Icon(painter = painterResource(R.drawable.ic_left_arrow), contentDescription = "Go Back", tint = AccentBlue, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.size(24.dp))
        Heading4(classroomName)
        Spacer(modifier = Modifier.size(24.dp))
        if(isEditable){
            Box(modifier = Modifier.size(24.dp).clickable(indication = null, interactionSource = null) { openMenu = true }){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Open Menu", tint = AccentBlue, modifier = Modifier.size(24.dp))
                if(openMenu){
                    DropdownMenu(expanded = openMenu, onDismissRequest = {openMenu = false}) {
                        DropdownMenuItem(text = { Text(text = "Create Course") }, onClick = {
                            openMenu = false
                            onNavigateToCreateCourse()

                        })
                        DropdownMenuItem(text = { Text(text = "Create Event") }, onClick = {
                            openMenu = false
                            onNavigateToCreateEvent()
                        })
                        DropdownMenuItem(text = { Text(text = "Create Schedule") }, enabled = isScheduleEnabled, onClick = {
                            openMenu = false
                            onNavigateToCreateSchedule()
                        })
                        DropdownMenuItem(text = { Text(text = "Create Assignment") }, enabled = isAssignmentEnabled, onClick = {
                            openMenu = false
                            onNavigateToCreateAssignment()
                        })
                        DropdownMenuItem(text = { Text(text = "Upload File") }, enabled = isFilesEnabled, onClick = {
                            openMenu = false
                            onNavigateToUploadFile()
                        })
                    }
                }
            }
        }else{
            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClassroomDetailsScreenPreview(){
    ClassroomDetailsScreen(id = "123", onNavigateToClassroomsList = {})
}

