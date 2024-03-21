package com.dvidal.core.domain

import android.content.SharedPreferences
import com.dvidal.core.constants.ConstantsPreferences
import com.dvidal.core.domain.model.ActivityLevel
import com.dvidal.core.domain.model.Gender
import com.dvidal.core.domain.model.GoalType
import com.dvidal.core.domain.model.UserInfo
import com.dvidal.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPrefs: SharedPreferences
): Preferences {

    override fun saveGender(gender: Gender) {
        sharedPrefs.edit()
            .putString(ConstantsPreferences.KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPrefs.edit()
            .putInt(ConstantsPreferences.KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPrefs.edit()
            .putFloat(ConstantsPreferences.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPrefs.edit()
            .putInt(ConstantsPreferences.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPrefs.edit()
            .putString(ConstantsPreferences.KEY_ACTIVITY_LEVEL, level.name)
            .apply()
    }

    override fun saveGoalType(type: GoalType) {
        sharedPrefs.edit()
            .putString(ConstantsPreferences.KEY_GOAL_TYPE, type.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPrefs.edit()
            .putFloat(ConstantsPreferences.KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPrefs.edit()
            .putFloat(ConstantsPreferences.KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPrefs.edit()
            .putFloat(ConstantsPreferences.KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        val age = sharedPrefs.getInt(ConstantsPreferences.KEY_AGE, -1)
        val height = sharedPrefs.getInt(ConstantsPreferences.KEY_HEIGHT, -1)
        val weight = sharedPrefs.getFloat(ConstantsPreferences.KEY_WEIGHT, -1f)
        val genderString = sharedPrefs.getString(ConstantsPreferences.KEY_GENDER, null)
        val activityLevelString = sharedPrefs
            .getString(ConstantsPreferences.KEY_ACTIVITY_LEVEL, null)
        val goalType = sharedPrefs.getString(ConstantsPreferences.KEY_GOAL_TYPE, null)
        val carbRatio = sharedPrefs.getFloat(ConstantsPreferences.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPrefs.getFloat(ConstantsPreferences.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPrefs.getFloat(ConstantsPreferences.KEY_FAT_RATIO, -1f)

        return UserInfo(
            gender = Gender.fromString(genderString ?: "male"),
            age = age,
            weight = weight,
            height = height,
            activityLevel = ActivityLevel.fromString(activityLevelString ?: "medium"),
            goalType = GoalType.fromString(goalType ?: "keep_weight"),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }
}