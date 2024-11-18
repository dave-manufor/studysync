package com.example.studysyncapp.navigation

import com.example.studysyncapp.R


val agenda = NavBarItem(title = "Agenda", route = Routes.Agenda, unselectedIcon = R.drawable.ic_agenda, selectedIcon = R.drawable.ic_agenda_accent)
val courses = NavBarItem(title = "Courses", route = Routes.Courses, unselectedIcon = R.drawable.ic_courses, selectedIcon = R.drawable.ic_courses_accent)
val tasks = NavBarItem(title = "Assignments", route = Routes.Assignments, unselectedIcon = R.drawable.ic_tasks, selectedIcon = R.drawable.ic_tasks_accent)
val classroom = NavBarItem(title = "Classrooms", route = Routes.Classrooms, unselectedIcon = R.drawable.ic_classroom, selectedIcon = R.drawable.ic_classroom_accent)
val more = NavBarItem(title = "More", route = Routes.More, unselectedIcon = R.drawable.ic_ellipses, selectedIcon = R.drawable.ic_ellipses_accent)

val NavBarItemsList = listOf(agenda, courses, tasks, classroom, more)