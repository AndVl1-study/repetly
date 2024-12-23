package com.andvl.repetly.features.homework.data.repository

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.features.homework.data.models.HomeworkItem
import com.andvl.repetly.features.homework.data.models.HomeworkUser

interface HomeworkRepository {

    suspend fun getHomework(id : Id) : HomeworkItem
    suspend fun getUser(id : Id) : HomeworkUser
    suspend fun sendHomeworkMessage(lessonId : Id, message : String)

}
