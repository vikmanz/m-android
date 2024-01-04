package com.vikmanz.shpppro.presentation.navigator

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    data class ToActivity(val directions: NavDirections) : NavigationCommand()
    data object Back : NavigationCommand()
}