package com.andvl.repetly.features.navigation.routes

import com.andvl.repetly.core.navigation.NavigationRoute

sealed class AuthScreen(override val route: String) : NavigationRoute {
    object Login : AuthScreen(route = "login")
    object RoleSelection : AuthScreen(route = "role")
    object SignUp : AuthScreen(route = "signup")
}
