package com.example.studysyncapp.domain.repository

import arrow.core.Either
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.Schedule

interface CoursesRepository {
    suspend fun getCourses(): Either<AppError, List<Course>>
    suspend fun getCoursesByClassroomId(id: String): Either<AppError, List<Course>>
    suspend fun insertCourse(course: Course): Either<AppError, Course>
    suspend fun updateCourse(course: Course): Either<AppError, Course>
    suspend fun deleteCourse(id: String): Either<AppError, Course>
}