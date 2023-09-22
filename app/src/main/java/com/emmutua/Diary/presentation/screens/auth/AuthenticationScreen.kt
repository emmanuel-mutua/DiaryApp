package com.emmutua.Diary.presentation.screens.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AuthenticationScreen(
    oneTapState: OneTapSignInState,
    loadingState: Boolean,
    onButtonClicked: () -> Unit,
    onTokenReceived: (String) -> Unit,
    onDialogDismissed : (String) -> Unit
) {
    Scaffold(
        content = {
            AuthenticationContent(
                loadingState = loadingState,
                onButtonClicked = onButtonClicked,
            )
        },
    )
    OneTapSignIn(
        state = oneTapState,
        onTokenIdReceived = { tokenId ->
            onTokenReceived(tokenId)
           onDialogDismissed("Success")
        },
        onDialogDismissed = {
            onDialogDismissed(it)
        },
    )
}
