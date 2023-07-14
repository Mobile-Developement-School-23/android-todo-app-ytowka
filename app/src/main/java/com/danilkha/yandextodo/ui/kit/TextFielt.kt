package com.danilkha.yandextodo.ui.kit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.ui.theme.body

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    text: String = "",
    updateTextAction: (text: String) -> Unit = {}
){
    Card(
        modifier = modifier.padding(bottom = 28.dp)
    ){
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 104.dp),
            value = text,
            onValueChange = { updateTextAction(it) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.task_editor_hint),
                    style = body
                )
            }
        )
    }
}