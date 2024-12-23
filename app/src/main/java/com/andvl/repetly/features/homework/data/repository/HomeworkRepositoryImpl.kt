package com.andvl.repetly.features.homework.data.repository

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.lesson.Attachment
import com.andvl.repetly.common.firestore.FirestoreRepository
import com.andvl.repetly.features.homework.data.models.HomeworkItem
import com.andvl.repetly.features.homework.data.models.HomeworkUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeworkRepositoryImpl @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : HomeworkRepository {

    override suspend fun sendHomeworkMessage(lessonId: Id, message: String) =
        withContext(Dispatchers.IO) {
            firestoreRepository.sendHomeworkMessage(lessonId, message)
        }

    override suspend fun getHomework(id: Id): HomeworkItem = withContext(Dispatchers.IO) {
        val lesson = firestoreRepository.getLesson(id)

        val images = mutableListOf<Attachment.Image>()
        lesson.homework?.attachments?.forEach {
            if (it is Attachment.Image) {
                images.add(it)
            }
        }
        HomeworkItem(
            title = lesson.subject.name,
            author = getUser(lesson.tutor.id),
            description = lesson.homework?.text ?: "",
            images = images
        )

    }

    override suspend fun getUser(id: Id): HomeworkUser {
        val user = firestoreRepository.getUser(id)
        return HomeworkUser(
            id = user.id,
            name = user.name,
            surname = user.surname,
            photoSrc = user.photoSrc
        )
    }

}
