package com.dvidal.tracker_domain.usecase

import com.dvidal.tracker_domain.model.TrackedFood
import com.dvidal.tracker_domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(trackedFood: TrackedFood) {
        return repository.deleteTrackedFood(trackedFood)
    }
}