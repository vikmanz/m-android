package com.vikmanz.shpppro.ui.navigator

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    data class ToActivity(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
}