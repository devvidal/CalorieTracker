package com.dvidal.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.core.R
import com.dvidal.core.domain.usecase.FilterOutDigits
import com.dvidal.core.util.UiEvent
import com.dvidal.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.dvidal.core.domain.preferences.Preferences
import com.dvidal.core.navigation.Route

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
): ViewModel() {

    var age by mutableStateOf("30")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String) {
        if (age.length <= 3)
            this.age = filterOutDigits.invoke(age)
    }

    fun onNextClick() = viewModelScope.launch {
        val ageNumber = age.toIntOrNull() ?: run {
            _uiEvent.send(
                UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_age_cant_be_empty))
            )
            return@launch
        }
        preferences.saveAge(ageNumber)
        _uiEvent.send(UiEvent.Navigate(Route.HEIGHT))
    }
}