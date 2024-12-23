package com.andvl.repetly.features.navigation.routes

import com.andvl.repetly.core.navigation.NavigationRoute

sealed class LessonCreationScreen(override val route: String) : NavigationRoute {
    object NewLesson : LessonCreationScreen(route = "new_lesson")
    object NewLessonSecond : LessonCreationScreen(route = "new_lesson_2")
}
