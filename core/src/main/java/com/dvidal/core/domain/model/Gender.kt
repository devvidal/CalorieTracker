package com.dvidal.core.domain.model

sealed class Gender(val name: String) {
    data object Male: Gender("male")
    data object Female: Gender("female")

    companion object {
        fun fromString(name: String): Gender {
            return when(name) {
                Male.name -> Male
                Female.name -> Female
                else -> Male
            }
        }
    }
}