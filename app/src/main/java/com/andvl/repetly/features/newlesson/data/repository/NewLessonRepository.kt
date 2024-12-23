package com.andvl.repetly.features.newlesson.data.repository

import android.net.Uri
import com.andvl.repetly.common.data.models.lesson.Attachment
import com.andvl.repetly.common.data.models.lesson.Subject
import com.andvl.repetly.features.newlesson.data.models.NewLessonItem
import com.andvl.repetly.features.newlesson.data.models.NewLessonUserItem

interface NewLessonRepository {

    suspend fun getSubjects(filter: String): List<String>
    suspend fun getSubject(subjectName: String): Subject?
    suspend fun saveNewLesson(lesson: NewLessonItem)
    suspend fun getStudents(): List<NewLessonUserItem>?
    suspend fun uploadImages(images: List<Attachment.Image>): List<String>
    suspend fun uploadFile(uri: Uri): String
    suspend fun getUserType() : Boolean
}
