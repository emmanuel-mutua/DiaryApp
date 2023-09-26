package com.emmutua.Diary.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMenuClicked: () -> Unit,
    filterWithDate: () -> Unit,
    navigateToWrite: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            HomeAppBar(
                onMenuClicked = onMenuClicked,
                filterWithDate = filterWithDate
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToWrite) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "Navigate to Write"
                )
            }
        }
    ) {
    }
}