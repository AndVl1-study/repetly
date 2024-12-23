package com.andvl.repetly.features.students.data

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.user.User

interface StudentsRepository {

    suspend fun getUserStudents() : List<User>?
    suspend fun getStudents() : List<User>
    suspend fun removeStudent(studentId : Id)
    suspend fun addStudent(studentId: Id)

}
