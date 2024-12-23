package com.andvl.repetly.features.navigation.routes

import com.andvl.repetly.core.navigation.NavigationRoute

enum class ProfileScreen(override val route: String) : NavigationRoute {
    ABOUT("change_about"), SUBJECTS("tutor_subjects"), STUDENTS("tutor_students")
}
