package com.emmutua.Diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.emmutua.Diary.navigation.Screen
import com.emmutua.Diary.navigation.SetUpNavGraph
import com.emmutua.Diary.ui.theme.DiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DiaryTheme {
                val navController = rememberNavController()
                SetUpNavGraph(
                    navController = navController,
                    startDestination = Screen.Authentication.route,
                )
            }
        }
    }
}
