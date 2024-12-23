package com.andvl.repetly.common.data.models.lesson

import com.andvl.repetly.common.data.models.Id

data class Subject(
    val id: Id,
    val name: String
) {
    companion object {
        val None = Subject(Id("0"), "None")
    }
}
