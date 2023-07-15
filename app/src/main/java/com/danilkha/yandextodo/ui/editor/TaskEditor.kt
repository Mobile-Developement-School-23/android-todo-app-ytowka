package com.danilkha.yandextodo.ui.editor

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.ui.kit.DatePickerBottomSheet
import com.danilkha.yandextodo.ui.kit.DeleteTaskButton
import com.danilkha.yandextodo.ui.kit.TextField
import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.theme.YandexTodoTheme
import com.danilkha.yandextodo.ui.theme.body
import com.danilkha.yandextodo.ui.theme.bottomSheetShape
import com.danilkha.yandextodo.ui.theme.button
import com.danilkha.yandextodo.ui.theme.subhead
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskEditor(
    state: TaskEditorState,
    onBackClicked: () -> Unit,
    onTextUpdate: (String) -> Unit,
    onDateSelected: (Date?) -> Unit,
    onSaveClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onImportanceSelected: (Importance) -> Unit,
) {
    var currentBottomSheetContent by remember { mutableStateOf<BottomSheetContent?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = {
            if(it == ModalBottomSheetValue.Hidden){
                currentBottomSheetContent = null
            }
            true
        }
    )
    LaunchedEffect(currentBottomSheetContent){
        if(currentBottomSheetContent != null){
            bottomSheetState.show()
        }else{
            bottomSheetState.hide()
        }
    }
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetShape = bottomSheetShape,
        sheetContent = {
            when(currentBottomSheetContent){
                BottomSheetContent.ImportancePicker -> DatePickerBottomSheet(
                    onDateSelected = {
                        val calendar = Calendar.getInstance()
                        calendar.set(it.year, it.monthValue - 1, it.dayOfMonth, 0, 0, 0)
                        calendar.set(Calendar.MILLISECOND, 0)
                        onDateSelected(calendar.time)
                        currentBottomSheetContent = null
                    }
                )
                BottomSheetContent.DatePicker -> ImportancePicker {
                    currentBottomSheetContent = null
                    onImportanceSelected(it)
                }
                null -> Unit
            }
        },
        sheetState = bottomSheetState,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            HeaderBlock(
                onClose = onBackClicked,
                onSave = onSaveClicked
            )
            TextField(
                text = state.task.text,
                onValueChange = onTextUpdate,
                hint = stringResource(R.string.task_editor_hint)
            )
            ImportanceBlock(
                importance = state.task.importance,
                onClick = {
                    currentBottomSheetContent = BottomSheetContent.ImportancePicker
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            DeadlineBlock(
                date = state.task.time,
                onCheck = {
                    if(it){
                        currentBottomSheetContent = BottomSheetContent.DatePicker
                    }else{
                        onDateSelected(null)
                    }
                }
            )
            Spacer(modifier = Modifier.size(24.dp))
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            DeleteTaskButton(
                modifier = Modifier.padding(16.dp),
                onClick = onDeleteClicked,
                isEditorMode = state.isEditorMode
            )
        }
    }
}

@Composable
fun HeaderBlock(
    onClose: () -> Unit = {},
    onSave: () -> Unit = {}
){
    Row(modifier = Modifier.padding(8.dp)){
        IconButton(
            onClick = onClose
        ) {
            Icon(
                imageVector = Icons.Sharp.Close,
                contentDescription = null,
                tint = colorResource(R.color.label_primary)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onSave
        ) {
            Text(
                text = stringResource(id = R.string.task_editor_save).toUpperCase(),
                style = button
            )
        }
    }
}

@Composable
fun ImportanceBlock(
    importance: Importance,
    onClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clickable (onClick = onClick),
    ) {
        Text(
            text = stringResource(R.string.task_editor_importance),
            style = body,
            color = colorResource(R.color.label_primary)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = stringResource( when(importance){
                Importance.LOW ->  R.string.importance_low
                Importance.NORMAL -> R.string.no
                Importance.HIGH -> R.string.importance_high
            }),
            style = subhead,
            color = colorResource(R.color.label_tertiary)
        )
    }
}

@Composable
fun ImportancePicker(
    onImportanceSelected: (Importance) -> Unit,
){
    Column {
        Importance.values().forEach {
            Text(
                modifier = Modifier
                    .clickable { onImportanceSelected(it) }
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(
                    when (it) {
                        Importance.LOW -> R.string.importance_low
                        Importance.NORMAL -> R.string.no
                        Importance.HIGH -> R.string.importance_high },
                ),
                style = body.copy(textAlign = TextAlign.Center),
                color = colorResource(if(it != Importance.HIGH) R.color.label_primary else R.color.color_red)
            )
        }
        Spacer(Modifier.size(36.dp))
    }
}

enum class BottomSheetContent { ImportancePicker, DatePicker }

@Composable
fun DeadlineBlock(
    date: Date?,
    onCheck: (Boolean) -> Unit,
){
    val dateFormatter = remember { SimpleDateFormat("dd MMMM yyyy") }
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.task_deadline),
                style = body,
                color = colorResource(R.color.label_primary),
            )
            if(date != null){
                Text(
                    text = dateFormatter.format(date),
                    style = subhead,
                    color = colorResource(R.color.color_blue),
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Switch(
            checked = date != null,
            onCheckedChange = onCheck,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary
            )
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun taskEditorPreview(){
    YandexTodoTheme {
        val task = TodoItem(
            id = "",
            text = "text",
            importance = Importance.HIGH,
            time = Date(),
            completed = true,
            createdAt = Date(),
            updatedAt = Date()
        )
        TaskEditor(
            state = TaskEditorState(
                task = task,
                isEditorMode = true
            ),
            onBackClicked = {},
            onTextUpdate = {},
            onDateSelected = {},
            onSaveClicked = {},
            onDeleteClicked = {},
            onImportanceSelected = {}
        )
    }
}