package com.andvl.repetly.common.data.models.user

import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.location.Location

data class Student(
    override val id: Id,
    override val name: String = "",
    override val surname: String = "",
    override val middleName: String? = null,
    override val about: String? = null,
    override val photoSrc: String? = null,
    override val phoneNumber: String,
    override val location: Location? = null,
    override val isTutor: Boolean = false,
) : User
