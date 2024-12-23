package com.andvl.repetly.features.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.core.navigation.sharedViewModel
import com.andvl.repetly.features.navigation.routes.Graph
import com.andvl.repetly.features.navigation.routes.TutorViewScreen
import com.andvl.repetly.features.tutorprofile.presentation.ui.TutorProfileScreen
import com.andvl.repetly.features.tutorprofile.presentation.viewmodel.TutorProfileViewModel

fun NavGraphBuilder.tutorViewNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.TUTOR_VIEW.route,
        startDestination = TutorViewScreen.TutorView.route
    ) {
        composable(route = TutorViewScreen.TutorView.route) { backStackEntry ->
            backStackEntry.arguments?.getString(TutorViewScreen.TutorView.ID_PARAM)?.let { id ->
                val viewModel = backStackEntry.sharedViewModel<TutorProfileViewModel>(navController)

                TutorProfileScreen(
                    tutorId = Id(id),
                    onBackClicked = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
        }
    }
}
