package com.vikmanz.shpppro.ui.navigator

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    // хранит значение NavDirections. Это значение мы будем использовать для навигации между фрагментами.
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    // просто объект. Мы будем использовать это значение для перехода назад
    object Back : NavigationCommand()
}