package com.example.fragmentsnavigatortest.screens.base

import java.io.Serializable

/**
 * Base class for defining screen arguments
 */
interface BaseScreen {
    val name: String
    val initialValue: String
}