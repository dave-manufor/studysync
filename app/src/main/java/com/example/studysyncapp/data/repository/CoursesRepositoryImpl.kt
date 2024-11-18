package com.example.studysyncapp.data.repository

import arrow.core.Either
import com.example.studysyncapp.data.mapper.toAppError
import com.example.studysyncapp.data.remote.CoursesApi
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.repository.CoursesRepository


class CoursesRepositoryImpl() : CoursesRepository {
    private val coursesApi = CoursesApi()

    override suspend fun getCourses(): Either<AppError, List<Course>> {
        return Either.catch {
            coursesApi.getCourses()
        }.mapLeft { it.toAppError() }
    }

    override suspend fun insertCourse(course: Course): Either<AppError, Course> {
        return Either.catch {
            coursesApi.insertCourse(course)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun updateCourse(course: Course): Either<AppError, Course> {
        return Either.catch {
            coursesApi.updateCourse(course)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun deleteCourse(id: String): Either<AppError, Course> {
        return Either.catch {
            coursesApi.deleteCourse(id)
        }.mapLeft { it.toAppError() }
    }
}