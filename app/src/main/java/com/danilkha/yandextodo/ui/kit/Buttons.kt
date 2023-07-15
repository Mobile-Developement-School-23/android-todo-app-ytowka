package com.danilkha.yandextodo.ui.kit

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danilkha.yandextodo.R

@Composable
@Preview
fun DeleteTaskButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isEditorMode: Boolean = false
){
    TextButton(
        modifier = modifier.padding(start = 2.dp),
        onClick = { onClick() },
        enabled = isEditorMode
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = "delete",
            tint = colorResource(if (isEditorMode) R.color.color_red else R.color.label_disable)
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = stringResource(id = R.string.delete),
            color = colorResource(if (isEditorMode) R.color.color_red else R.color.label_disable)
        )
    }
}