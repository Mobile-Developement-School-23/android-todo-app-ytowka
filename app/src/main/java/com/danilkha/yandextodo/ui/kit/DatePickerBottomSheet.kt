package com.danilkha.yandextodo.ui.kit

import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.icons.LibIcons
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@Composable
fun DatePickerBottomSheet(
    onDateSelected: (LocalDate) -> Unit,
) {
    CalendarView(
        selection = CalendarSelection.Date{
            onDateSelected(it)
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            icons = LibIcons.Filled
        ),
        useCaseState =  rememberUseCaseState(visible = true)
    )
}