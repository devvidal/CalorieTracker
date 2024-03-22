package com.dvidal.onboarding_presentation.activitylevel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.dvidal.core.util.UiEvent
import com.dvidal.core_ui.LocalSpacing
import com.dvidal.core.R
import com.dvidal.core.domain.model.ActivityLevel
import com.dvidal.core.domain.model.Gender
import com.dvidal.onboarding_presentation.components.ActionButton
import com.dvidal.onboarding_presentation.components.SelectableButton

@Composable
fun ActivityLevelScreen(
    viewModel: ActivityLevelViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate.invoke(event)
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_activity_level),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.spaceMedium))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.low),
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.Low,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityLevelSelect(ActivityLevel.Low) }
                )
                Spacer(modifier = Modifier.width(LocalSpacing.current.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.medium),
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.Medium,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityLevelSelect(ActivityLevel.Medium) }
                )
                Spacer(modifier = Modifier.width(LocalSpacing.current.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.high),
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.High,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityLevelSelect(ActivityLevel.High) }
                )
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}