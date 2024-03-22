package com.dvidal.onboarding_domain.usecase

import com.dvidal.core.R
import com.dvidal.core.util.UiText

class ValidateNutrients {
    operator fun invoke(
        carbsRatioText: String,
        proteinRatioText: String,
        fatRatioText: String
    ): Result {
        val carbsRatio = carbsRatioText.toIntOrNull()
        val proteinsRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()

        if (carbsRatio == null || proteinsRatio == null || fatRatio == null)
            return Result.Error(UiText.StringResource(R.string.error_invalid_values))

        if (carbsRatio + proteinsRatio + fatRatio != 100)
            return Result.Error(UiText.StringResource(R.string.error_not_100_percent))

        return Result.Success(
            carbsRatio = fatRatio / 100f,
            proteinRatio = proteinsRatio / 100f,
            fatRatio = fatRatio / 100f
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ) : Result()

        data class Error(val message: UiText) : Result()
    }
}