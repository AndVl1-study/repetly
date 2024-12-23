package com.andvl.repetly.features.subjects.data

import com.andvl.repetly.common.data.models.lesson.Subject
import com.andvl.repetly.common.data.models.lesson.SubjectWithPrice

interface SubjectsRepository {

    suspend fun getSubjects() : List<Subject>
    suspend fun getUserSubjects() : List<SubjectWithPrice>?
    suspend fun updateUserSubjects(userSubjects : List<SubjectWithPrice>?)

}
