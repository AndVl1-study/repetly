package com.andvl.repetly.features.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andvl.repetly.features.about.presentation.ui.AboutScreen
import com.andvl.repetly.features.about.presentation.viewmodel.AboutNavigationEvent
import com.andvl.repetly.features.about.presentation.viewmodel.AboutViewModel
import com.andvl.repetly.features.messenger.presentation.ui.ChatsScreen
import com.andvl.repetly.features.messenger.presentation.viewmodel.MessengerViewModel
import com.andvl.repetly.features.navigation.routes.BottomBarScreen
import com.andvl.repetly.features.navigation.routes.Graph
import com.andvl.repetly.features.navigation.routes.LessonViewScreen
import com.andvl.repetly.features.navigation.routes.ProfileScreen
import com.andvl.repetly.features.navigation.routes.TutorViewScreen
import com.andvl.repetly.features.schedule.presentation.ui.ScheduleScreen
import com.andvl.repetly.features.schedule.presentation.viewmodel.ScheduleNavigationEvent
import com.andvl.repetly.features.schedule.presentation.viewmodel.ScheduleViewModel
import com.andvl.repetly.features.students.presentation.ui.StudentsScreen
import com.andvl.repetly.features.students.presentation.viewmodel.StudentsNavigationEvent
import com.andvl.repetly.features.students.presentation.viewmodel.StudentsViewModel
import com.andvl.repetly.features.subjects.presentation.ui.SubjectsScreen
import com.andvl.repetly.features.subjects.presentation.viewmodel.SubjectViewModel
import com.andvl.repetly.features.subjects.presentation.viewmodel.SubjectsNavigationEvent
import com.andvl.repetly.features.tutorsearch.presentation.ui.SearchScreen
import com.andvl.repetly.features.tutorsearch.presentation.viewmodel.TutorSearchNavigationEvent
import com.andvl.repetly.features.tutorsearch.presentation.viewmodel.TutorSearchViewModel
import com.andvl.repetly.features.userprofile.presentation.ui.ProfileScreen
import com.andvl.repetly.features.userprofile.presentation.viewmodel.ProfileViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        route = Graph.HOME.route,
        startDestination = BottomBarScreen.Home.route,
        modifier = modifier
    ) {
        tutorViewNavGraph(navController)

        composable(route = BottomBarScreen.Search.route) {
            val tutorSearchViewModel = hiltViewModel<TutorSearchViewModel>()

            val lifecycleOwner = LocalLifecycleOwner.current
            val navigationEvents = tutorSearchViewModel.navigationEvents
            val uiEvents = tutorSearchViewModel.uiEvents
            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    navigationEvents.collect { navigationEvent ->
                        when (navigationEvent) {
                            is TutorSearchNavigationEvent.NavigateBack -> navController.popBackStack()
                            is TutorSearchNavigationEvent.NavigateToTutorProfile -> navController.navigate(
                                TutorViewScreen.TutorView.createTutorViewRoute(navigationEvent.tutor.id)
                            )
                        }
                    }
                }
            }

            SearchScreen(
                state = tutorSearchViewModel.state.collectAsState().value,
                uiEvents = uiEvents,
                onAction = tutorSearchViewModel::onAction
            )
        }

        composable(route = BottomBarScreen.Home.route) {
            val scheduleViewModel = hiltViewModel<ScheduleViewModel>()

            val lifecycleOwner = LocalLifecycleOwner.current
            val navigationEvents = scheduleViewModel.navigationEvents
            val uiEvents = scheduleViewModel.uiEvents
            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    navigationEvents.collect { navigationEvent ->
                        when (navigationEvent) {
                            is ScheduleNavigationEvent.NavigateToLesson -> navController.navigate(
                                LessonViewScreen.Lesson.createLessonRoute(navigationEvent.lesson.id)
                            )

                            ScheduleNavigationEvent.NavigateToNewLesson -> navController.navigate(
                                Graph.LESSON_CREATION.route
                            )
                        }
                    }
                }
            }

            ScheduleScreen(
                scheduleUiState = scheduleViewModel.state.collectAsState().value,
                uiEvents = uiEvents,
                onAction = scheduleViewModel::onAction
            )
        }

        lessonViewNavGraph(navController)

        lessonCreationNavGraph(navController)

        composable(route = BottomBarScreen.Chats.route) {
            val messengerViewModel = hiltViewModel<MessengerViewModel>()
//            HomeworkScreen()
            ChatsScreen(
                getChats = messengerViewModel::getChats,
                chatsUiState = messengerViewModel.chatsUiState
            )
        }

        composable(route = BottomBarScreen.Profile.route) {
            val profileViewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                profileScreenUiState = profileViewModel.uiState.collectAsState().value,
                sideEffectState = profileViewModel.sideEffect,
                onOptionNavigate = { navController.navigate(it.route) },
                onOptionClicked = profileViewModel::onOptionClicked
            )
        }

        composable(route = ProfileScreen.ABOUT.route) {
            val aboutViewModel = hiltViewModel<AboutViewModel>()

            val lifecycleOwner = LocalLifecycleOwner.current
            val navigationEvents = aboutViewModel.navigationEvents
            val uiEvents = aboutViewModel.uiEvents
            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    navigationEvents.collect { navigationEvent ->
                        when (navigationEvent) {
                            AboutNavigationEvent.NavigateBack -> navController.popBackStack()
                            AboutNavigationEvent.ChangedSuccessful -> {
                                navController.popBackStack(
                                    BottomBarScreen.Profile.route,
                                    true
                                )
                                navController.navigate(BottomBarScreen.Profile.route)
                            }
                        }
                    }
                }
            }
            AboutScreen(
                state = aboutViewModel.state.collectAsState().value,
                onAction = aboutViewModel::onAction,
                uiEvents = uiEvents
            )
        }

        composable(route = ProfileScreen.SUBJECTS.route) {
            val subjectsViewModel = hiltViewModel<SubjectViewModel>()

            val lifecycleOwner = LocalLifecycleOwner.current
            val navigationEvents = subjectsViewModel.navigationEvents
            val uiEvents = subjectsViewModel.uiEvents
            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    navigationEvents.collect { navigationEvent ->
                        when (navigationEvent) {
                            SubjectsNavigationEvent.NavigateBack -> navController.popBackStack()
                        }
                    }
                }
            }

            SubjectsScreen(
                state = subjectsViewModel.state.collectAsState().value,
                uiEvents = uiEvents,
                onAction = subjectsViewModel::onAction
            )
        }

        composable(route = ProfileScreen.STUDENTS.route) {
            val studentsViewModel = hiltViewModel<StudentsViewModel>()

            val lifecycleOwner = LocalLifecycleOwner.current
            val navigationEvents = studentsViewModel.navigationEvents
            val uiEvents = studentsViewModel.uiEvents
            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    navigationEvents.collect { navigationEvent ->
                        when (navigationEvent) {
                            StudentsNavigationEvent.NavigateBack -> navController.popBackStack()
                        }
                    }
                }
            }

            StudentsScreen(
                state = studentsViewModel.state.collectAsState().value,
                uiEvents = uiEvents,
                onAction = studentsViewModel::onAction
            )
        }
    }
}
