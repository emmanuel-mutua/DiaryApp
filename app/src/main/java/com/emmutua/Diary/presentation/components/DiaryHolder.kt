package com.emmutua.Diary.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emmutua.Diary.model.Diary
import com.emmutua.Diary.ui.theme.Elevation

@Composable
fun DiaryHolder(
    diary: Diary?,
    onClick: (String) -> Unit = {}
) {
    var componentHeight by remember {
        mutableStateOf(0.dp)
    }
    Row(
        modifier = Modifier.clickable(
            indication = null, interactionSource = remember {
                MutableInteractionSource()
            }
        ) {
            onClick(diary?._id.toString() ?: "2")
        }
    ) {
        Spacer(modifier = Modifier.width(14.dp))
        Surface(
            modifier = Modifier
                .width(2.dp)
                .height(14.dp),
            tonalElevation = Elevation.level1
        ) {}
        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DiaryHolderPrev() {
    DiaryHolder(
        diary = null
    )
}

