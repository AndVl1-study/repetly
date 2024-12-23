package com.andvl.repetly.features.schedule.data;

import com.andvl.repetly.common.data.models.lesson.Lesson
import com.andvl.repetly.common.data.sources.FakeLessonSource
import com.andvl.repetly.common.firestore.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate;

import javax.inject.Inject;

class ScheduleRepositoryImpl @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ScheduleRepository {
    override suspend fun getLessons(): List<Lesson> = withContext(Dispatchers.IO) {
        firestoreRepository.getLessons()
    }

    override suspend fun getLessons(day:LocalDate): List<Lesson> = withContext(Dispatchers.IO) {
        FakeLessonSource.loadLessons().filter {
            it.startTime.year == day.year && it.startTime.dayOfYear == day.dayOfYear
        }
    }

    override suspend fun getUserType(): Boolean {
        return firestoreRepository.getCurrentUserType()
    }
}
