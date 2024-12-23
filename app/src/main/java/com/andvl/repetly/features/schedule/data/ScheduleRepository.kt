package com.andvl.repetly.features.schedule.data

import com.andvl.repetly.common.data.models.lesson.Lesson
import java.time.LocalDate

interface ScheduleRepository {
    suspend fun getLessons(): List<Lesson>
    suspend fun getLessons(day: LocalDate): List<Lesson>
    suspend fun getUserType() : Boolean
}
