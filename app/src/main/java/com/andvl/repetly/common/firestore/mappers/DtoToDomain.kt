package com.andvl.repetly.common.firestore.mappers

import com.google.firebase.Timestamp
import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.contact.Contact
import com.andvl.repetly.common.data.models.contact.ContactType
import com.andvl.repetly.common.data.models.education.Education
import com.andvl.repetly.common.data.models.education.EducationType
import com.andvl.repetly.common.data.models.language.Language
import com.andvl.repetly.common.data.models.language.LanguageLevel
import com.andvl.repetly.common.data.models.language.LanguageWithLevel
import com.andvl.repetly.common.data.models.lesson.Attachment
import com.andvl.repetly.common.data.models.lesson.Homework
import com.andvl.repetly.common.data.models.lesson.Lesson
import com.andvl.repetly.common.data.models.lesson.Review
import com.andvl.repetly.common.data.models.lesson.Subject
import com.andvl.repetly.common.data.models.lesson.SubjectWithPrice
import com.andvl.repetly.common.data.models.user.Student
import com.andvl.repetly.common.data.models.user.Tutor
import com.andvl.repetly.common.data.models.user.User
import com.andvl.repetly.common.firestore.models.AttachmentDto
import com.andvl.repetly.common.firestore.models.ContactDto
import com.andvl.repetly.common.firestore.models.EducationDto
import com.andvl.repetly.common.firestore.models.EducationTypeDto
import com.andvl.repetly.common.firestore.models.HomeworkDto
import com.andvl.repetly.common.firestore.models.LanguageDto
import com.andvl.repetly.common.firestore.models.LanguageLevelDto
import com.andvl.repetly.common.firestore.models.LanguageWithLevelDto
import com.andvl.repetly.common.firestore.models.LessonDto
import com.andvl.repetly.common.firestore.models.ReviewDto
import com.andvl.repetly.common.firestore.models.SubjectDto
import com.andvl.repetly.common.firestore.models.SubjectWithPriceDto
import com.andvl.repetly.common.firestore.models.UserDto
import java.time.LocalDateTime
import java.time.ZoneId

fun ReviewDto.toDomain(): Review {
    return Review(
        id = Id(this.id),
        text = this.text,
        attachments = if (this.attachments.isEmpty()) null else this.attachments.map { it.toDomain() }
    )
}

fun HomeworkDto.toDomain(): Homework {
    return Homework(
        text = this.text,
        authorID = Id(this.authorId),
        reviews = if (this.reviews.isEmpty()) null else this.reviews.map { it.toDomain() },
        attachments = if (this.attachments.isEmpty()) null else this.attachments.map { it.toDomain() }
    )
}

fun AttachmentDto.toDomain(): Attachment {
    return when {
        this.image != null -> Attachment.Image(
            url = this.image.url,
            description = this.image.description
        )

        this.file != null -> Attachment.File(
            url = this.file.url,
            fileName = this.file.fileName
        )

        else -> throw IllegalArgumentException("Invalid attachment data")
    }
}

fun SubjectDto.toDomain(): Subject {
    return Subject(
        id = Id(id),
        name = name
    )
}

fun LessonDto.toDomain(
    subject: Subject,
    tutor: Tutor
): Lesson {
    return Lesson(
        id = Id(this.id),
        subject = subject,
        topic = this.topic,
        description = this.description,
        tutor = tutor,
        studentIds = this.studentIds.map { Id(it) },
        startTime = this.startTime.toLocalDateTime(),
        endTime = this.endTime.toLocalDateTime(),
        homework = this.homework?.toDomain(),
        additionalMaterials = this.additionalMaterials
    )
}

fun Timestamp.toLocalDateTime(): LocalDateTime {
    val instant = this.toDate().toInstant()
    return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"))
}

fun SubjectWithPriceDto.toDomain(
    subject: Subject
): SubjectWithPrice {
    return SubjectWithPrice(
        subject = subject,
        price = this.price
    )
}

fun UserDto.toDomain(
    subjectsPrices: List<SubjectWithPrice>? = null,
    languagesWithLevels: List<LanguageWithLevel>? = null,
    educations: List<Education>? = null,
    contacts: List<Contact>? = null
): User {
    return if (this.canBeTutor) {
        this.toDomainTutor(
            subjectsPrices = subjectsPrices,
            languagesWithLevels = languagesWithLevels,
            educations = educations,
            contacts = contacts
        )
    } else {
        this.toDomainStudent()
    }
}

fun UserDto.toDomainStudent(): Student {
    return Student(
        id = Id(this.id),
        name = this.name,
        surname = this.surname,
        middleName = this.middleName,
        about = this.about,
        photoSrc = this.photoSrc,
        phoneNumber = this.phoneNumber,
        location = null,
        isTutor = false,
    )
}

fun UserDto.toDomainTutor(
    subjectsPrices: List<SubjectWithPrice>? = null,
    languagesWithLevels: List<LanguageWithLevel>? = null,
    educations: List<Education>? = null,
    contacts: List<Contact>? = null
): Tutor {
    val subjects = subjectsPrices?.map { it.subject }

    return Tutor(
        id = Id(this.id),
        name = this.name,
        surname = this.surname,
        middleName = this.middleName,
        about = this.about,
        photoSrc = this.photoSrc,
        phoneNumber = this.phoneNumber,
        location = null,
        isTutor = true,
        subjects = subjects,
        educations = educations,
        subjectsPrices = subjectsPrices,
        averagePrice = this.averagePrice,
        rating = this.averageRating,
        reviewsNumber = this.reviewsNumber,
        taughtLessonNumber = this.taughtLessonNumber,
        experienceYears = this.experienceYears,
        languagesWithLevels = languagesWithLevels,
        contacts = contacts
    )
}

fun LanguageWithLevelDto.toDomain(
    language: Language
): LanguageWithLevel {
    return LanguageWithLevel(
        language = language,
        level = LanguageLevel.getLevelByString(this.level)
    )
}

fun EducationTypeDto.toDomain(): EducationType {
    return EducationType.fromId(Id(this.id))
}

fun EducationDto.toDomain(
    type: EducationType
): Education {
    return Education(
        id = Id(this.id),
        type = type,
        specialization = this.specialization
    )
}

fun LanguageLevelDto.toDomain(): LanguageLevel {
    return LanguageLevel.fromId(Id(this.id))
}

fun LanguageDto.toDomain(): Language {
    return Language(
        id = Id(this.id),
        name = this.name
    )
}

fun ContactDto.toDomain(): Contact {
    return Contact(
        id = Id(this.id),
        value = this.value,
        type = ContactType.getTypeByString(this.type).value
    )
}
