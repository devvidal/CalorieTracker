package com.dvidal.tracker_presentation.trackeroverview.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dvidal.core.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun parseDateText(date: LocalDate): String {
    val today = LocalDate.now()

    return when(date) {
        today -> stringResource(id = R.string.today)
        today.minusDays(1) -> stringResource(id = R.string.yesterday)
        today.plusDays(1) -> stringResource(id = R.string.tomorrow)
        else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)
    }
}