package com.dvidal.onboarding_presentation.height

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

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
): ViewModel() {

    var height by mutableStateOf("180")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height: String) {
        if (height.length <= 3)
            this.height = filterOutDigits.invoke(height)
    }

    fun onNextClick() = viewModelScope.launch {
        val heightNumber = height.toIntOrNull() ?: run {
            _uiEvent.send(
                UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_height_cant_be_empty))
            )
            return@launch
        }
        preferences.saveHeight(heightNumber)
        _uiEvent.send(UiEvent.Success)
    }
}