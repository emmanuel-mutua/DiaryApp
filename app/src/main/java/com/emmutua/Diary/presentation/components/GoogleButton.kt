package com.emmutua.Diary.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emmutua.Diary.R

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    loadingState: Boolean = false,
    primaryText: String = "Sign in with Google",
    secondaryText: String = "Please Wait ...",
    shape: Shape = Shapes().extraSmall,
    borderStrokeWidth: Dp = 1.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    icon: Int = R.drawable.google_logo,
    onClick: () -> Unit,
) {
    var buttonText by remember {
        mutableStateOf(primaryText)
    }
    LaunchedEffect(key1 = loadingState) {
        buttonText = if (loadingState) secondaryText else primaryText
    }
    Surface(
        modifier = modifier.clickable(enabled = !loadingState) { onClick() },
        shape = shape,
        border = BorderStroke(width = borderStrokeWidth, color = borderColor),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Logo",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buttonText,
                style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
            )
            if (loadingState) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = progressIndicatorColor,
                    strokeWidth = 2.dp,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GoogleButtonPreview() {
    GoogleButton {}
}

@Preview(showSystemUi = true)
@Composable
fun GoogleButtonPreview2() {
    GoogleButton(loadingState = true) {}
}
