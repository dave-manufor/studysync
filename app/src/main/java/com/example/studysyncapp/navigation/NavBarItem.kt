package com.example.studysyncapp.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val title: String,
    val route: Routes,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val badgeCount: Int? = null
)
