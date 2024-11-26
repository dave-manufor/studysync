package com.example.studysyncapp.presentation.classroom.details

import android.util.Log
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.data.repository.AssignmentsRepositoryImpl
import com.example.studysyncapp.data.repository.ClassroomsRepositoryImpl
import com.example.studysyncapp.data.repository.CoursesRepositoryImpl
import com.example.studysyncapp.data.repository.EventsRepositoryImpl
import com.example.studysyncapp.data.repository.FilesRepositoryImpl
import com.example.studysyncapp.data.repository.SchedulesRepositoryImpl
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Classroom
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.Schedule
import com.example.studysyncapp.domain.model.event.Event
import com.example.studysyncapp.domain.model.event.EventType
import com.example.studysyncapp.domain.model.file.File
import com.example.studysyncapp.domain.repository.AssignmentsRepository
import com.example.studysyncapp.domain.repository.ClassroomsRepository
import com.example.studysyncapp.domain.repository.CoursesRepository
import com.example.studysyncapp.domain.repository.EventsRepository
import com.example.studysyncapp.domain.repository.FilesRepository
import com.example.studysyncapp.domain.repository.SchedulesRepository
import com.example.studysyncapp.presentation.agenda.CreateEventDialogController
import com.example.studysyncapp.presentation.assignments.CreateAssignmentDialogController
import com.example.studysyncapp.presentation.courses.CreateCourseDialogController
import com.example.studysyncapp.presentation.courses.CreateScheduleDialogController
import com.example.studysyncapp.utils.DaysOfTheWeek
import com.example.studysyncapp.utils.toUTC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.UUID

class ClassroomDetailsViewModel(private val classroomId: String): ViewModel(), UploadFileDialogController, CreateAssignmentDialogController,
    CreateCourseDialogController, CreateScheduleDialogController, CreateEventDialogController {
    private val coursesRepository: CoursesRepository = CoursesRepositoryImpl()
    private val assignmentsRepository: AssignmentsRepository = AssignmentsRepositoryImpl()
    private val schedulesRepository: SchedulesRepository = SchedulesRepositoryImpl()
    private val eventsRepository: EventsRepository = EventsRepositoryImpl()
    private val classroomRepository: ClassroomsRepository = ClassroomsRepositoryImpl()
    private val filesRepository: FilesRepository = FilesRepositoryImpl()
    private val _state = MutableStateFlow(ClassroomViewState())
    val state: StateFlow<ClassroomViewState> = _state.asStateFlow()

    init{
        Log.d("MYTAG", "ClassroomDetailsViewModel init called")
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            async{ getClassroom() }.await().let {
                checkEditAuthority()
                async { getCourses() }
                async { getAssignments() }
                async { getSchedules() }
                async { getEvents() }
                async { getFiles() }
            }
        }
    }

    private fun setError(error: AppError){
        _state.update { it.copy(error = error.message, isLoading = false) }
    }

    private fun setLoading(isLoading: Boolean){
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun setClassroom(classroom: Classroom?){
        if(classroom == null){
            setError(AppError(message = "Classroom not found"))
            return
        }
        _state.update { it.copy(classroom = classroom, isLoading = false) }
    }

    private suspend fun getClassroom(){
        val classroom = classroomRepository.getClassroomById(classroomId)
        when(classroom){
            is Either.Left -> setError(classroom.value)
            is Either.Right -> setClassroom(classroom.value)
        }
    }

    private suspend fun getCourses(){
        val courses = coursesRepository.getCoursesByClassroomId(classroomId)
        when(courses){
            is Either.Left -> setError(courses.value)
            is Either.Right -> _state.update { it.copy(courses = courses.value) }
        }
    }

    private suspend fun getAssignments(){
        val assignments = assignmentsRepository.getAssignmentsByClassroomId(classroomId)
        when(assignments){
            is Either.Left -> setError(assignments.value)
            is Either.Right -> _state.update { it.copy(assignments = assignments.value) }
        }
    }

    private suspend fun getSchedules(){
        val schedules = schedulesRepository.getSchedulesByClassroomId(classroomId)
        when(schedules){
            is Either.Left -> setError(schedules.value)
            is Either.Right -> _state.update { it.copy(schedules = schedules.value) }
        }
    }

    private suspend fun getEvents(){
        val events = eventsRepository.getEventsByClassroomId(classroomId)
        when(events){
            is Either.Left -> setError(events.value)
            is Either.Right -> _state.update { it.copy(events = events.value) }
        }
    }

    private suspend fun getFiles(){
        _state.value.classroom?.id?.let {
            val files = filesRepository.getClassroomFiles(it)
            when(files){
                is Either.Left -> setError(files.value)
                is Either.Right -> _state.update { it.copy(files = files.value) }
            }
        }
    }

    private suspend fun checkEditAuthority(){
        val userId = getUserId()
        _state.update { it.copy(isEditable = state.value.classroom?.user_id == userId) }
    }

    override fun onDismissUploadFile() {
        _state.update { it.copy(showUploadFileDialog = false) }
    }

    override fun onOpenUploadFile() {
        _state.update { it.copy(showUploadFileDialog = true) }
    }

    override fun onConfirmUploadFile(
        isAssignment: Boolean,
        course: Course?,
        assignment: Assignment?,
        file: File
    ) {
        if(isAssignment){
            if(assignment == null){
                setError(AppError(message = "Assignment not found"))
                return
            }else{
                viewModelScope.launch(Dispatchers.IO) {
                    val result = filesRepository.attachFileToAssignment(assignment.id, file.id)
                    when(result){
                        is Either.Left -> setError(result.value)
                        is Either.Right -> {
                            getFiles()
                        }
                    }
                    if(_state.value.error.isNullOrEmpty()){
                        _state.update { it.copy(showUploadFileDialog = false) }
                    }
                }
            }
        }else{
            if(course == null){
                setError(AppError(message = "Course not found"))
                return
            }else{
                viewModelScope.launch(Dispatchers.IO) {
                    val result = filesRepository.attachFileToCourse(course.id, file.id)
                    when(result){
                        is Either.Left -> setError(result.value)
                        is Either.Right -> {
                            getFiles()
                        }
                    }
                    if(_state.value.error.isNullOrEmpty()){
                        _state.update { it.copy(showUploadFileDialog = false) }
                    }
                }
            }
        }
    }

    fun onFileClick(file: File, uriHandler: UriHandler){
        viewModelScope.launch(Dispatchers.IO){
            file.path?.let {
                val signedUrl = filesRepository.getSignedUrl(it)
                when(signedUrl){
                    is Either.Left -> setError(signedUrl.value)
                    is Either.Right -> uriHandler.openUri(signedUrl.value)
                }
            }
        }
    }

    override fun onOpenCreateAssignment() {
        _state.update { it.copy(showCreateAssignmentDialog = true) }
    }

    override fun onDismissCreateAssignment() {
        _state.update { it.copy(showCreateAssignmentDialog = false) }
    }

    override fun onConfirmCreateAssignment(
        assignmentName: String,
        assignmentDescription: String,
        courseId: String,
        dueAt: String,
        classroomId: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val assignment = Assignment(
                id = UUID.randomUUID().toString(),
                name = assignmentName,
                description = assignmentDescription,
                classroom_id = classroomId,
                course_id = courseId,
                due_at = dueAt,
                user_id = getUserId()
            )
            val newAssignment = assignmentsRepository.insertAssignment(assignment)
            when (newAssignment) {
                is Either.Left -> setError(newAssignment.value)
                is Either.Right -> {
                    getAssignments()
                }
            }
            if(_state.value.error.isNullOrEmpty()){
                _state.update { it.copy(showCreateAssignmentDialog = false) }
            }
        }
    }

    override fun onConfirmCreateCourse(
        courseName: String,
        courseColor: String,
        courseDescription: String,
        classroomId: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val course = Course(
                id = UUID.randomUUID().toString(),
                name = courseName,
                color = courseColor,
                description = courseDescription,
                user_id = getUserId(),
                classroom_id = classroomId)
            val newCourse = coursesRepository.insertCourse(course)
            when (newCourse) {
                is Either.Left -> setError(newCourse.value)
                is Either.Right -> {
                    getCourses()
                }
            }
            if(_state.value.error.isNullOrEmpty()){
                _state.update { it.copy(showCreateCourseDialog = false) }
            }
        }
    }

    override fun onOpenCreateCourse() {
        _state.update { it.copy(showCreateCourseDialog = true) }
    }

    override fun onDismissCreateCourse() {
        _state.update { it.copy(showCreateCourseDialog = false) }
    }

    override fun onOpenCreateSchedule() {
        _state.update { it.copy(showCreateScheduleDialog = true) }
    }

    override fun onDismissCreateSchedule() {
        _state.update { it.copy(showCreateScheduleDialog = false) }
    }

    override fun onConfirmCreateSchedule(
        course: Course,
        day: DaysOfTheWeek,
        startTime: LocalTime,
        endTime: LocalTime,
        online: Boolean,
        location: String?,
        classroomId: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSchedule = schedulesRepository.insertSchedule(
                Schedule(
                    id = UUID.randomUUID().toString(), course_id = course.id,
                    user_id = getUserId(),
                    classroom_id = classroomId,
                    day_of_the_week = day.value,
                    starts_at = startTime.toUTC(),
                    ends_at = endTime.toUTC(),
                    online = online,
                    location = location,
                )
            )
            when(newSchedule){
                is Either.Left -> setError(newSchedule.value)
                is Either.Right -> {
                    getSchedules()
                }
            }
            if(_state.value.error.isNullOrEmpty()){
                _state.update { it.copy(showCreateScheduleDialog = false) }
            }
        }
    }

    override fun onConfirmCreateEvent(
        eventName: String,
        eventType: EventType,
        eventDescription: String,
        eventStartDate: String,
        eventEndDate: String,
        classroomId: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val event = Event(
                id = UUID.randomUUID().toString(),
                name = eventName,
                type = eventType,
                description = eventDescription,
                user_id = getUserId(),
                classroom_id = classroomId,
                starts_at = eventStartDate,
                ends_at = eventEndDate,
            )
            val newEvent = eventsRepository.insertEvent(event)
            when (newEvent) {
                is Either.Left -> setError(newEvent.value)
                is Either.Right -> {
                    getEvents()
                }
            }
            if (_state.value.error.isNullOrEmpty()) {
                _state.update { it.copy(showCreateEventDialog = false) }
            }
        }
    }

    override fun onOpenCreateEvent() {
        _state.update { it.copy(showCreateEventDialog = true) }
    }

    override fun onDismissCreateEvent() {
        _state.update { it.copy(showCreateEventDialog = false) }
    }
}