package com.dvidal.onboarding_presentation.weight

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
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
): ViewModel() {

    var weight by mutableStateOf("70.0")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String) {
        if (weight.length <= 5)
            this.weight = weight
    }

    fun onNextClick() = viewModelScope.launch {
        val weightNumber = weight.toFloatOrNull() ?: run {
            _uiEvent.send(
                UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_weight_cant_be_empty))
            )
            return@launch
        }
        preferences.saveWeight(weightNumber)
        _uiEvent.send(UiEvent.Navigate(Route.ACTIVITY_LEVEL))
    }
}