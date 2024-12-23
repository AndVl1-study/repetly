package com.andvl.repetly.common.data.models.location

data class Location(
    var country: Country,
    var city: City? = null,
)
