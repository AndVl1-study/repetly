package com.andvl.repetly.common.firestore.mappers

import com.google.firebase.Timestamp
import com.andvl.repetly.common.data.models.contact.Contact
import com.andvl.repetly.common.data.models.education.Education
import com.andvl.repetly.common.data.models.language.Language
import com.andvl.repetly.common.data.models.language.LanguageWithLevel
import com.andvl.repetly.common.data.models.lesson.Attachment
import com.andvl.repetly.common.data.models.lesson.Homework
import com.andvl.repetly.common.data.models.lesson.Lesson
import com.andvl.repetly.common.data.models.lesson.Review
import com.andvl.repetly.common.data.models.lesson.SubjectWithPrice
import com.andvl.repetly.common.firestore.models.AttachmentDto
import com.andvl.repetly.common.firestore.models.ContactDto
import com.andvl.repetly.common.firestore.models.EducationDto
import com.andvl.repetly.common.firestore.models.FileDto
import com.andvl.repetly.common.firestore.models.HomeworkDto
import com.andvl.repetly.common.firestore.models.ImageDto
import com.andvl.repetly.common.firestore.models.LanguageDto
import com.andvl.repetly.common.firestore.models.LanguageWithLevelDto
import com.andvl.repetly.common.firestore.models.LessonDto
import com.andvl.repetly.common.firestore.models.ReviewDto
import com.andvl.repetly.common.firestore.models.SubjectWithPriceDto
import java.time.LocalDateTime
import java.time.ZoneOffset

fun Lesson.toDto(): LessonDto {
    return LessonDto(
        tutorId = this.tutor.id.value,
        studentIds = this.studentIds.map { it.value },
        subjectId = this.subject.id.value,
        topic = this.topic,
        startTime = this.startTime.toTimestamp(),
        endTime = this.endTime.toTimestamp(),
        homework = this.homework?.toDto(),
        additionalMaterials = this.additionalMaterials ?: ""
    )
}

fun LocalDateTime.toTimestamp(): Timestamp {
    val instant = this.toInstant(ZoneOffset.UTC)
    return Timestamp(instant.epochSecond, instant.nano)
}

fun Homework.toDto(): HomeworkDto {
    return HomeworkDto(
        authorId = this.authorID.value,
        text = this.text,
        reviews = this.reviews?.map { it.toDto() } ?: emptyList(),
        attachments = this.attachments?.map { it.toDto() } ?: emptyList()
    )
}

fun Review.toDto(): ReviewDto {
    return ReviewDto(
        text = this.text,
        attachments = if (this.attachments == null) emptyList() else this.attachments.map { it.toDto() }
    )
}

fun Attachment.toDto(): AttachmentDto {
    return when (this) {
        is Attachment.Image -> AttachmentDto(
            image = ImageDto(
                url = this.url,
                description = this.description ?: ""
            )
        )

        is Attachment.File -> AttachmentDto(
            file = FileDto(
                url = this.url,
                fileName = this.fileName
            )
        )
    }
}

fun LanguageWithLevel.toDto(): LanguageWithLevelDto {
    return LanguageWithLevelDto(
        languageId = this.language.id.value,
        level = this.level.value
    )
}

fun Language.toDto(): LanguageDto {
    return LanguageDto(
        id = this.id.value,
        name = this.name
    )
}

fun Education.toDto(): EducationDto {
    return EducationDto(
        id = this.id.value,
        typeId = this.type.id.value,
        specialization = this.specialization
    )
}

fun SubjectWithPrice.toDto() : SubjectWithPriceDto {
    return SubjectWithPriceDto(
        subjectId = subject.id.value,
        price = price
    )
}

fun Contact.toDto(): ContactDto {
    return ContactDto(
        id = this.id.value,
        value = this.value,
        type = this.type
    )
}
