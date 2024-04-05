package com.dvidal.tracker_presentation.trackeroverview

import com.dvidal.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {

    data object OnNextDayClick: TrackerOverviewEvent()
    data object OnPreviousDayClick: TrackerOverviewEvent()
    data class OnToggleMealClick(val meal: Meal): TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood): TrackerOverviewEvent()
    data class OnAddFoodClick(val meal: Meal): TrackerOverviewEvent()
}