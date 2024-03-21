package com.dvidal.core.domain.model

sealed class ActivityLevel(val name: String) {
    data object Low: ActivityLevel("low")
    data object Medium: ActivityLevel("medium")
    data object High: ActivityLevel("high")

    companion object {
        fun fromString(name: String): ActivityLevel {
            return when(name) {
                Low.name -> Low
                Medium.name -> Medium
                High.name -> High
                else -> Medium
            }
        }
    }
}