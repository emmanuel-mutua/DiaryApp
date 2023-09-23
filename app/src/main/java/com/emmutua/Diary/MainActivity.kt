package com.emmutua.Diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.emmutua.Diary.navigation.Screen
import com.emmutua.Diary.navigation.SetUpNavGraph
import com.emmutua.Diary.ui.theme.DiaryTheme
import com.emmutua.Diary.utils.Constants.APP_ID
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DiaryTheme {
                val navController = rememberNavController()
                SetUpNavGraph(
                    navController = navController,
                    startDestination = getStartDestination(),
                )
            }
        }
    }
}

private fun getStartDestination(): String {
    val user = App.Companion.create(APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.Authentication.route
    else Screen.Authentication.route
}