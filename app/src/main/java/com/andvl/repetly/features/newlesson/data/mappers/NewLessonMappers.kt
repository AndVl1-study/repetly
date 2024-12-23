package com.andvl.repetly.features.newlesson.data.mappers

import com.andvl.repetly.common.data.models.user.Student
import com.andvl.repetly.features.newlesson.data.models.NewLessonUserItem

fun Student.toNewLessonUserItem(): NewLessonUserItem {
    return NewLessonUserItem(
        id = this.id,
        name = this.name,
        surname = this.surname,
        photoSrc = this.photoSrc
    )
}
