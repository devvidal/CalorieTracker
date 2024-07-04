package com.dvidal.tracker_domain.model

sealed class MealType(val name: String) {

    data object Breakfast : MealType("breakfast")
    data object Lunch : MealType("lunch")
    data object Dinner : MealType("dinner")
    data object Snack : MealType("snack")

    companion object {
        fun fromString(name: String): MealType {
            return when (name.lowercase()) {
                Breakfast.name -> Breakfast
                Lunch.name -> Lunch
                Dinner.name -> Dinner
                Snack.name -> Snack
                else -> Breakfast
            }
        }
    }
}
