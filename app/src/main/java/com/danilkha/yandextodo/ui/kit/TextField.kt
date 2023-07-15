package com.danilkha.yandextodo.ui.kit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.ui.theme.body

@Composable
fun TextField(
    text: String,
    onValueChange: (String) -> Unit,
    hint: String = ""
){
    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Box(modifier = Modifier
            .padding(16.dp)
        ){
            if (text.isEmpty()) {
                Text(
                    text = hint,
                    style = body,
                    color = colorResource(R.color.label_tertiary)
                )
            }
            BasicTextField(
                modifier = Modifier
                    .heightIn(min = 104.dp)
                    .fillMaxWidth(),
                textStyle = body.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                value = text,
                onValueChange = onValueChange,

            )
        }
    }
}