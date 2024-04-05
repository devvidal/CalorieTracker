package com.dvidal.core.domain.preferences

import com.dvidal.core.domain.model.ActivityLevel
import com.dvidal.core.domain.model.Gender
import com.dvidal.core.domain.model.GoalType
import com.dvidal.core.domain.model.UserInfo

interface Preferences {

    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveHeight(height: Int)
    fun saveActivityLevel(level: ActivityLevel)
    fun saveGoalType(type: GoalType)
    fun saveCarbRatio(ratio: Float)
    fun saveProteinRatio(ratio: Float)
    fun saveFatRatio(ratio: Float)

    fun loadUserInfo(): UserInfo

    fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun loadShouldShowOnboarding(): Boolean
}