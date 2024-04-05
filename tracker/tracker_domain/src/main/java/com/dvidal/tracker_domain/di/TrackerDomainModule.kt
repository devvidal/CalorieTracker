package com.dvidal.tracker_domain.di

import com.dvidal.core.domain.preferences.Preferences
import com.dvidal.tracker_domain.repository.TrackerRepository
import com.dvidal.tracker_domain.usecase.CalculateMealNutrients
import com.dvidal.tracker_domain.usecase.DeleteTrackedFood
import com.dvidal.tracker_domain.usecase.GetFoodsForDate
import com.dvidal.tracker_domain.usecase.SearchFood
import com.dvidal.tracker_domain.usecase.TrackFood
import com.dvidal.tracker_domain.usecase.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
    }
}