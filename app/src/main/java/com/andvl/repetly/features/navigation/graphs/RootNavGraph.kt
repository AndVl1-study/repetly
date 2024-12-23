package com.andvl.repetly.features.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andvl.repetly.features.auth.presentation.viewmodel.AuthViewModel
import com.andvl.repetly.features.navigation.presentation.ui.HomeScreen
import com.andvl.repetly.features.navigation.routes.Graph

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val isUserAuthenticated =
        authViewModel.isUserAuthenticated.collectAsState(initial = false).value

    NavHost(
        navController = navController,
        route = Graph.ROOT.route,
        startDestination = if (isUserAuthenticated) Graph.HOME.route else Graph.AUTHENTICATION.route,
    ) {
        authNavGraph(navController)

        composable(route = Graph.HOME.route) {
            HomeScreen()
        }
    }
}
