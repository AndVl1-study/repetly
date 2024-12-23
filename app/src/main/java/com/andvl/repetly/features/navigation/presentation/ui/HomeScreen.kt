package com.andvl.repetly.features.navigation.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andvl.repetly.features.navigation.graphs.HomeNavGraph
import com.andvl.repetly.features.navigation.routes.BottomBarScreen

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            repetlyBottomBar(
                tabs = listOf(
                    BottomBarScreen.Search,
                    BottomBarScreen.Home,
//                    BottomBarScreen.Chats,
                    BottomBarScreen.Profile
                ).toTypedArray(),

                navController = navController
            )
        }
    ) { innerPaddingModifier ->
        HomeNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPaddingModifier)
        )
    }
}
