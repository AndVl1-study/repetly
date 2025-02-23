package com.andvl.repetly.features.currentlesson.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.lesson.Lesson
import com.andvl.repetly.features.currentlesson.data.CurrentLessonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject


sealed interface CurrentLessonUiState {
    data class Success(val lesson: Lesson) : CurrentLessonUiState
    object Error : CurrentLessonUiState
    object Loading : CurrentLessonUiState
}

@HiltViewModel
class CurrentLessonViewModel @Inject constructor(
    private val currentLessonRepository: CurrentLessonRepository
) : ViewModel() {
    var currentLessonUiState: CurrentLessonUiState by mutableStateOf(CurrentLessonUiState.Loading)
        private set

    fun getLesson(lessonId: Id) {
        viewModelScope.launch {
            currentLessonUiState = CurrentLessonUiState.Loading
            currentLessonUiState = try {
                val lesson = currentLessonRepository.getLesson(lessonId)
                CurrentLessonUiState.Success(lesson)
            } catch (e: IOException) {
                CurrentLessonUiState.Error
            } catch (e: HttpRetryException) {
                CurrentLessonUiState.Error
            }
        }
    }
}
