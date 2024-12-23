package com.andvl.repetly.features.currentlesson.data

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.lesson.Lesson
import com.andvl.repetly.common.data.sources.FakeLessonSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeCurrentLessonRepository @Inject constructor() : CurrentLessonRepository {
    override suspend fun getLesson(id: Id): Lesson = withContext(Dispatchers.IO) {
        FakeLessonSource.getLesson(id)
    }

    override suspend fun addLesson(lesson: Lesson) = withContext(Dispatchers.IO) {
        FakeLessonSource.addLesson(lesson)
    }
}
