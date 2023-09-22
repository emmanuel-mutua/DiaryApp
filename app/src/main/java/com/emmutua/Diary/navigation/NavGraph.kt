package com.emmutua.Diary.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emmutua.Diary.presentation.screens.auth.AuthenticationScreen
import com.emmutua.Diary.presentation.screens.auth.AuthenticationViewModel
import com.emmutua.Diary.presentation.screens.auth.rememberOneTapSignInState
import com.emmutua.Diary.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        authenticationRoute()
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute() {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val loadingState by viewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        var message by remember {
            mutableStateOf("")
        }
        AuthenticationScreen(
            oneTapState = oneTapState,
            loadingState = loadingState,
            onButtonClicked = {
                viewModel.setLoadingState(true)
                oneTapState.open()
            },
            onDialogDismissed = {
                  message = it
            },
            onTokenReceived = {
                Log.d("TOKEN","$it")
                viewModel.loginWithMongodbAtlas(
                    tokenId = it,
                    onSuccess = {
                            message = "Success"
                    },
                    onError = {
                        message = it.message.toString()
                    }
                )
                viewModel.setLoadingState(false)
            }
        )
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screen.Write.route,
        arguments = listOf(
            navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        ),
    ) {
    }
}