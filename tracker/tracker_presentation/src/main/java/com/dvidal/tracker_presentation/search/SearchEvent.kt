package com.dvidal.tracker_presentation.search

import com.dvidal.tracker_domain.model.MealType
import com.dvidal.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {

    data class OnQueryChange(val query: String) : SearchEvent()
    data object OnSearch : SearchEvent()
    data class OnToggleTrackableFood(val food: TrackableFood) : SearchEvent()
    data class OnAmountForFoodChange(
        val food: TrackableFood,
        val amount: String
    ) : SearchEvent()
    data class OnTrackFoodClick(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ) : SearchEvent()
    data class OnSearchFocusChange(val isFocused: Boolean) : SearchEvent()
}