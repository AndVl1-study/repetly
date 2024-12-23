package com.andvl.repetly.features.homework.presentation.ui.components

import androidx.compose.runtime.Composable
import com.andvl.repetly.core.ui.components.LessonTopBar

@Composable
fun HomeworkTopBar(
    title : String,
    onNavigateBack : () -> Unit
) {
    LessonTopBar(onNavigateBack = onNavigateBack, title = title)
}
