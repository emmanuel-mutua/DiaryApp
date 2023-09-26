package com.emmutua.Diary.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.ModifierLocal
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emmutua.Diary.presentation.screens.auth.AuthenticationScreen
import com.emmutua.Diary.presentation.screens.auth.AuthenticationViewModel
import com.emmutua.Diary.presentation.screens.auth.rememberOneTapSignInState
import com.emmutua.Diary.presentation.screens.home.HomeScreen
import com.emmutua.Diary.utils.Constants.APP_ID
import com.emmutua.Diary.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screen.Write.route)
            }
        )
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome : () -> Unit
) {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val isAuthenticated by viewModel.authenticated
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
            },
            isAuthenticated = isAuthenticated,
            navigateToHome = navigateToHome
        )
    }
}

fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            onMenuClicked = {},
            filterWithDate = {},
            navigateToWrite = navigateToWrite
        )
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
