package com.andvl.repetly.features.navigation.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.andvl.repetly.core.ui.theme.repetlyTheme
import com.andvl.repetly.features.navigation.graphs.RootNavGraph
import com.andvl.repetly.features.navigation.presentation.viewmodel.MainUiState
import com.andvl.repetly.features.navigation.presentation.viewmodel.MainViewModel

@Composable
fun repetlyApp() {
    val viewModel: MainViewModel = hiltViewModel()
    val mainUiState = viewModel.uiState.collectAsState()

    when (val currentState = mainUiState.value) {
        is MainUiState.Error -> {}
        is MainUiState.Loading -> {}

        is MainUiState.Success -> {
            repetlyTheme(darkTheme = currentState.isDarkThemeEnabled) {
                RootNavGraph(rememberNavController())
            }
        }
    }
}
