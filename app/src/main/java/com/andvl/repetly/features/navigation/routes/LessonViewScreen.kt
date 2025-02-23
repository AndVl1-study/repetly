package com.andvl.repetly.features.navigation.routes

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.core.navigation.NavigationRoute

sealed interface LessonViewScreen : NavigationRoute {
    override val route: String

    object Lesson : LessonViewScreen {
        private const val BASE_ROUTE = "lesson"
        const val ID_PARAM = "id"

        override val route: String = "$BASE_ROUTE/{$ID_PARAM}"
        fun createLessonRoute(lessonId: Id) = "$BASE_ROUTE/${lessonId.value}"
    }
    object Homework : LessonViewScreen {
        private const val BASE_ROUTE = "homework"
        const val ID_PARAM = "id"

        override val route: String = "$BASE_ROUTE/{$ID_PARAM}"
        fun createHomeworkRoute(lessonId: Id) = "$BASE_ROUTE/${lessonId.value}"
    }
}
