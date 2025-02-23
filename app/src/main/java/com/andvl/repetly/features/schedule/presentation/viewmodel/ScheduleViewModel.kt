package com.andvl.repetly.features.schedule.presentation.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andvl.repetly.R
import com.andvl.repetly.common.data.models.lesson.Lesson
import com.andvl.repetly.features.schedule.data.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import java.net.HttpRetryException
import java.time.LocalDate
import javax.inject.Inject

sealed interface ScheduleUiState {
    data class Success(val scheduleState: ScheduleState) : ScheduleUiState
    object Error : ScheduleUiState
    object Loading : ScheduleUiState
}

sealed interface ScheduleNavigationEvent {
    data class NavigateToLesson(val lesson: Lesson) : ScheduleNavigationEvent
    object NavigateToNewLesson : ScheduleNavigationEvent
}

sealed interface ScheduleAction {
    data class NavigateToLesson(val lesson: Lesson) : ScheduleAction
    object NavigateToNewLesson : ScheduleAction
    object RetryAction : ScheduleAction

    object Refresh : ScheduleAction
}

sealed class ScheduleUIEvent(@StringRes val msg: Int) {

    object RefreshFail : ScheduleUIEvent(R.string.schedule_screen_refresh_fail_msg)


}

data class ScheduleState(
    val lessons: Map<LocalDate, List<Lesson>> = emptyMap(),
    val isTutor: Boolean = false,
    val isRefreshing: Boolean = false,
)

private const val TAG = "SCHEDULE"

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val state = _state.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<ScheduleNavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val _uiEvents = MutableSharedFlow<ScheduleUIEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    private lateinit var lessonsList: List<Lesson>

    init {
        viewModelScope.launch {
            getLessons()
        }
    }


    fun getLessons() {
        viewModelScope.launch {
            _state.value = ScheduleUiState.Loading
            val lessonsCache = mutableMapOf<LocalDate, MutableList<Lesson>>()
            withContext(Dispatchers.IO) {
                try {
                    lessonsList = scheduleRepository.getLessons()
                    val isTutor = scheduleRepository.getUserType()
                    lessonsList.forEach { lesson ->
                        val day = LocalDate.from(lesson.startTime)
                        val dayLessons = lessonsCache.getOrPut(day) { mutableListOf() }
                        dayLessons.add(lesson)
                        dayLessons.sortBy { it.startTime }
                    }
                    _state.value =
                        ScheduleUiState.Success(
                            ScheduleState(
                                lessons = lessonsCache,
                                isTutor = isTutor
                            )
                        )
                } catch (e: IOException) {
                    Log.d(TAG, e.toString())
                    _state.value = ScheduleUiState.Error
                } catch (e: HttpRetryException) {
                    _state.value = ScheduleUiState.Error
                    Log.d(TAG, e.toString())
                } catch (e: Exception) {
                    _state.value = ScheduleUiState.Error
                    Log.d(TAG, e.toString())
                }
            }
        }
    }

    private suspend fun refresh() {
        when (val state = _state.value) {
            is ScheduleUiState.Success -> {
                _state.update {
                    state.copy(
                        state.scheduleState.copy(
                            isRefreshing = true
                        )
                    )
                }
                try {
                    withContext(Dispatchers.IO) {
                        val lessons = scheduleRepository.getLessons()
                        if (lessons == lessonsList) {
                            return@withContext
                        }
                        val lessonsCache = mutableMapOf<LocalDate, MutableList<Lesson>>()
                        lessons.forEach { lesson ->
                            val day = LocalDate.from(lesson.startTime)
                            val dayLessons = lessonsCache.getOrPut(day) { mutableListOf() }
                            dayLessons.add(lesson)
                            dayLessons.sortBy { it.startTime }
                        }
                        lessonsList = lessons
                        _state.update {
                            state.copy(
                                state.scheduleState.copy(
                                    lessons = lessonsCache
                                )
                            )
                        }
                    }
                } catch (e: IOException) {
                    _uiEvents.emit(ScheduleUIEvent.RefreshFail)
                } catch (e: HttpRetryException) {
                    _uiEvents.emit(ScheduleUIEvent.RefreshFail)
                } catch (e: Exception) {
                    _uiEvents.emit(ScheduleUIEvent.RefreshFail)
                }
                (_state.value as? ScheduleUiState.Success)?.let { successState ->
                    _state.update {
                        successState.copy(
                            scheduleState = successState.scheduleState.copy(
                                isRefreshing = false
                            )
                        )
                    }
                }

            }

            else -> {}
        }
    }

    fun onAction(action: ScheduleAction) {
        viewModelScope.launch {
            when (action) {
                is ScheduleAction.NavigateToLesson -> _navigationEvents.emit(
                    ScheduleNavigationEvent.NavigateToLesson(
                        action.lesson
                    )
                )

                ScheduleAction.NavigateToNewLesson -> _navigationEvents.emit(ScheduleNavigationEvent.NavigateToNewLesson)
                ScheduleAction.RetryAction -> getLessons()
                ScheduleAction.Refresh -> refresh()
            }
        }

    }
}
