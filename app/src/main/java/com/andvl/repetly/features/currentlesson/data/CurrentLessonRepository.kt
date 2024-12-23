package com.andvl.repetly.features.currentlesson.data

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.lesson.Lesson

interface CurrentLessonRepository {
    suspend fun getLesson(id: Id): Lesson

    suspend fun addLesson(lesson: Lesson)
}
