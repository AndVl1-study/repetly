package com.andvl.repetly.features.navigation.routes

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.core.navigation.NavigationRoute

sealed interface TutorViewScreen : NavigationRoute {
    override val route: String

    object TutorView : TutorViewScreen {
        private const val BASE_ROUTE = "tutor_view"
        const val ID_PARAM = "id"

        override val route: String = "$BASE_ROUTE/{$ID_PARAM}"
        fun createTutorViewRoute(tutorId: Id) = "$BASE_ROUTE/${tutorId.value}"
    }
}
