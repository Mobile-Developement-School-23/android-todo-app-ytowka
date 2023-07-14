package com.danilkha.yandextodo.ui.editor

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.ui.kit.DeleteTaskButton
import com.danilkha.yandextodo.ui.kit.TextField
import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.theme.body
import com.danilkha.yandextodo.ui.theme.button
import com.danilkha.yandextodo.ui.theme.subhead
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskEditor(
    state: TaskEditorState,
    onBackClicked: () -> Unit,
    onTextUpdate: (String) -> Unit,
    onDateChecked: (Boolean) -> Unit,
    onSaveClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onImportanceSelected: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ){
        Column {
            val highlightedState = remember{mutableStateOf(false)}
            Header(
                goBackAction = onBackClicked,
                saveButtonAction = onSaveClicked
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    text = state.task.text,
                    updateTextAction = onTextUpdate
                )
                ImportanceSection(
                    bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
                    importanceText = stringResource(when(state.task.importance){
                        Importance.LOW ->  R.string.importance_low
                        Importance.NORMAL -> R.string.no
                        Importance.HIGH -> R.string.importance_high
                    }),
                    highlightedState = highlightedState
                )
                Divider(
                    Modifier.padding(top = 16.dp, bottom = 16.dp)
                )
                DeadlineSection(
                    date = state.task.time,
                    updateDeadlineAction = onDateChecked
                )
            }
            Divider(
                Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            DeleteTaskButton(onDeleteClicked, isEditorMode = state.isEditorMode)
        }
    }
}

@Composable
@Preview
fun Header(
    goBackAction: () -> Unit = {},
    saveButtonAction: () -> Unit = {}
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { goBackAction() }
        ) {
            Icon(
                imageVector = Icons.Sharp.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        TextButton(
            modifier = Modifier.padding(end = 4.dp),
            onClick = { saveButtonAction() }
        ) {
            Text(
                text = stringResource(id = R.string.task_editor_save),
                style = button
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ImportanceSection(
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded
    ),
    importanceText: String,
    highlightedState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
){
    val scope = rememberCoroutineScope()

    var errorColor = MaterialTheme.colorScheme.errorContainer
    var color: Color by remember { mutableStateOf(errorColor) }
    LaunchedEffect(highlightedState.value){
        if(highlightedState.value){
            Log.w("AAA", "animate")
            animate(
                0f,
                2f,
                animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
            ){ value, _ ->
                color = if(value <= 1) color.copy(alpha = value)
                else color.copy(alpha = 2 - value)
            }
        }
        else{
            color = color.copy(alpha = 0f)
        }
        highlightedState.value = false
    }
    Column {
        Text(
            text = stringResource(id = R.string.task_editor_importance),
            Modifier
                .clickable {
                    scope.launch {
                        if(bottomSheetState.currentValue != ModalBottomSheetValue.Expanded){
                            bottomSheetState.show()
                        }
                        else{
                            bottomSheetState.hide()
                        }
                    }
                },
            style = body,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = importanceText,
            style = subhead,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .background(color = color)
        )
    }
}

@Composable
fun DeadlineSection(
    date: Date?,
    updateDeadlineAction: (isChecked: Boolean) -> Unit = {}
){
    val dateFormat = remember { SimpleDateFormat("dd MMMM yyyy") }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.task_deadline),
                style = body,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row {
                Text(
                    text = if (date != null) dateFormat.format(date) else "",
                    style = subhead,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Text(
                    text = if (date != null) dateFormat.format(date) else "",
                    style = subhead,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .padding(start = 20.dp)
                )
            }
        }
        val isChecked = remember { mutableStateOf(false) }
        Switch(
            checked = date != null,
            onCheckedChange = {
                isChecked.value = !isChecked.value
                updateDeadlineAction(it)
            }
        )
    }
}