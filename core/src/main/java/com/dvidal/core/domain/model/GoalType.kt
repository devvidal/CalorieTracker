package com.dvidal.core.domain.model

sealed class GoalType(val name: String) {
    data object LoseWeight: GoalType("lose_weight")
    data object KeepWeight: GoalType("keep_weight")
    data object GainWeight: GoalType("gain_weight")

    companion object {
        fun fromString(name: String): GoalType {
            return when(name) {
                LoseWeight.name -> LoseWeight
                KeepWeight.name -> KeepWeight
                GainWeight.name -> GainWeight
                else -> KeepWeight
            }
        }
    }
}