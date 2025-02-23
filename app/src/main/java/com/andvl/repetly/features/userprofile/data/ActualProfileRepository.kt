package com.andvl.repetly.features.userprofile.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.andvl.repetly.R
import com.andvl.repetly.common.firestore.FirestoreRepository
import com.andvl.repetly.features.userprofile.data.models.ProfileUserData
import javax.inject.Inject


enum class OptionType() {
    Tutor, Student, Setting
}

sealed interface TrailingContentType {
    object Empty : TrailingContentType

    data class ThemeSwitcher(
        val isEnabled: Boolean = false,
        val onSwitchCallback: (Boolean) -> Unit = {}
    ) :
        TrailingContentType

    data class HomeworkBadge(val count: Int = 0) : TrailingContentType
}


sealed interface Options {

    @get:DrawableRes
    val icon: Int

    @get:StringRes
    val text: Int
    val type: OptionType
    val trailingItem: TrailingContentType

    object Logout : Options {
        override val icon: Int = R.drawable.ic_logout
        override val text: Int = R.string.profile_screen_logout
        override val type: OptionType = OptionType.Setting
        override val trailingItem: TrailingContentType = TrailingContentType.Empty

    }


    object Subjects : Options {
        override val icon: Int = R.drawable.ic_subjects
        override val text: Int = R.string.profile_screen_subjects
        override val type: OptionType = OptionType.Tutor
        override val trailingItem: TrailingContentType = TrailingContentType.Empty
    }


    object LessonsTutor : Options {
        override val icon = R.drawable.ic_lessons
        override val text = R.string.profile_screen_lessons
        override val type = OptionType.Tutor
        override val trailingItem = TrailingContentType.Empty
    }

    object LessonsStudent : Options {
        override val icon = R.drawable.ic_lessons
        override val text = R.string.profile_screen_lessons
        override val type = OptionType.Student
        override val trailingItem = TrailingContentType.Empty
    }

    object Tutors : Options {
        override val icon = R.drawable.ic_students
        override val text = R.string.profile_screen_tutors
        override val type = OptionType.Student
        override val trailingItem = TrailingContentType.Empty
    }

    object Students : Options {
        override val icon = R.drawable.ic_students
        override val text = R.string.profile_screen_students
        override val type = OptionType.Tutor
        override val trailingItem = TrailingContentType.Empty
    }

    object Statistics : Options {
        override val icon = R.drawable.ic_statistics
        override val text = R.string.profile_screen_statistics
        override val type = OptionType.Tutor
        override val trailingItem = TrailingContentType.Empty
    }

    object AboutStudent : Options {
        override val icon = R.drawable.ic_about
        override val text = R.string.profile_screen_about
        override val type = OptionType.Student
        override val trailingItem = TrailingContentType.Empty
    }

    object AboutTutor : Options {
        override val icon = R.drawable.ic_about
        override val text = R.string.profile_screen_about
        override val type = OptionType.Tutor
        override val trailingItem = TrailingContentType.Empty
    }

    object Security : Options {
        override val icon = R.drawable.ic_security
        override val text = R.string.profile_screen_security
        override val type = OptionType.Setting
        override val trailingItem = TrailingContentType.Empty
    }

    object Notifications : Options {
        override val icon = R.drawable.ic_notifications
        override val text = R.string.profile_screen_notification
        override val type = OptionType.Setting
        override val trailingItem = TrailingContentType.Empty
    }

    object Language : Options {
        override val icon = R.drawable.ic_language
        override val text = R.string.profile_screen_language
        override val type = OptionType.Setting
        override val trailingItem = TrailingContentType.Empty
    }

    object Help : Options {
        override val icon = R.drawable.ic_help
        override val text = R.string.profile_screen_help
        override val type = OptionType.Setting
        override val trailingItem = TrailingContentType.Empty
    }

    data class Homework(override val trailingItem: TrailingContentType.HomeworkBadge) : Options {
        override val icon = R.drawable.ic_homework
        override val text = R.string.profile_screen_homework
        override val type = OptionType.Tutor
    }

    data class ThemeSwitch(
        override val trailingItem: TrailingContentType.ThemeSwitcher,
        override val icon: Int = R.drawable.ic_light_theme
    ) : Options {
        override val text = R.string.profile_screen_theme
        override val type = OptionType.Setting
    }

}

class ActualProfileRepository @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : UserProfileRepository {

    override suspend fun signOut() {
        firestoreRepository.signoutUser()
    }

    override suspend fun getSettingsOptions(): List<Options> =
        listOf(
//            Options.Security,
//            Options.Notifications,
//            Options.Language,
//            Options.Help,
            Options.ThemeSwitch(TrailingContentType.ThemeSwitcher()),
            Options.Logout
        )


    override suspend fun getUserOptions(): List<Options> {
        if (firestoreRepository.getCurrentUserType()) {
            return listOf(
                Options.LessonsTutor,
                Options.Subjects,
                Options.Students,
//                Options.Homework(TrailingContentType.HomeworkBadge()),
//                Options.Statistics,
                Options.AboutTutor
            )
        } else {
            return listOf(Options.LessonsStudent, Options.Tutors, Options.AboutStudent)
        }

    }


    override suspend fun getUserData(): ProfileUserData {
        val currentUser = firestoreRepository.getCurrentUser()
        return ProfileUserData(
            name = currentUser.name,
            surname = currentUser.surname,
            phoneNumber = currentUser.phoneNumber,
            photoSrc = currentUser.photoSrc,
            isTutor = currentUser.isTutor
        )
    }
}
