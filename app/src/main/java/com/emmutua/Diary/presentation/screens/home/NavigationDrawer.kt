package com.emmutua.Diary.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emmutua.Diary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
           ModalDrawerSheet {
               Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(250.dp),
                   contentAlignment = Alignment.Center
               ) {
                   Image(
                       modifier = Modifier.size(250.dp),
                       painter = painterResource(id = R.drawable.logo),
                       contentDescription = "Logo"
                   )
               }
               NavigationDrawerItem(
                   label = {
                       Row(modifier = Modifier.padding(horizontal = 12.dp),
                           verticalAlignment = Alignment.CenterVertically) {
                           Image(
                               modifier = Modifier.size(40.dp),
                               painter = painterResource(id = R.drawable.google_logo),
                               contentDescription = "Google Logo"
                           )
                           Spacer(modifier = Modifier.width(12.dp))
                           Text(text = "Sign Out")
                       }
                   },
                   selected = false,
                   onClick = onSignOutClicked
               )
           }
        },
        content = content
    )
}