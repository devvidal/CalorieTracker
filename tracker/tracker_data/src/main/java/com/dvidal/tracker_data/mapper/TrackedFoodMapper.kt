package com.dvidal.tracker_data.mapper

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.dvidal.tracker_data.local.entity.TrackedFoodEntity
import com.dvidal.tracker_domain.model.MealType
import com.dvidal.tracker_domain.model.TrackedFood
import java.time.LocalDate

@SuppressLint("NewApi")
fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id
    )
}

@SuppressLint("NewApi")
fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        calories = calories,
        id = id
    )
}