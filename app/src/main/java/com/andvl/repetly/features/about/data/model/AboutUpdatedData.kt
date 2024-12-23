package com.andvl.repetly.features.about.data.model

import com.andvl.repetly.common.data.models.education.Education
import com.andvl.repetly.common.data.models.language.LanguageWithLevel

data class AboutUpdatedData(
    val about : String? = null,
    val photoSrc : String? = null,
    val education : Education? = null,
    val languagesWithLevels: List<LanguageWithLevel>? = null,
    val contacts : List<AboutContactItem>? = null
)
