package com.dvidal.tracker_presentation.trackeroverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.core.domain.preferences.Preferences
import com.dvidal.core.util.UiEvent
import com.dvidal.tracker_domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
) : ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    init {
        refreshFoods()
        preferences.saveShouldShowOnboarding(false)
    }

    override fun onCleared() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = null
        super.onCleared()
    }

    fun onEvent(event: TrackerOverviewEvent) {
        viewModelScope.launch {
            when (event) {
                is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)
                    refreshFoods()
                }
                is TrackerOverviewEvent.OnNextDayClick -> {
                    state = state.copy(
                        date = state.date.plusDays(1)
                    ).also { refreshFoods() }
                }
                is TrackerOverviewEvent.OnPreviousDayClick -> {
                    state = state.copy(
                        date = state.date.minusDays(1)
                    ).also { refreshFoods() }
                }
                is TrackerOverviewEvent.OnToggleMealClick -> {
                    state = state.copy(
                        meals = state.meals.map {
                            if (it.name == event.meal.name) {
                                it.copy(isExpanded = !it.isExpanded)
                            } else it
                        }
                    )
                }
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases
            .getFoodsForDate(state.date)
            .onEach { foods ->
                val nutrientsResult = trackerUseCases.calculateMealNutrients(foods)
                state = state.copy(
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProteins = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    proteinGoal = nutrientsResult.proteinGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    trackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal =
                            nutrientsResult.mealNutrients[it.mealType]
                                ?: return@map it.copy(
                                    carbs = 0,
                                    protein = 0,
                                    fat = 0,
                                    calories = 0
                                )
                        it.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }
}