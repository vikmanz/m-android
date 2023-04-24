package com.example.fragmentsnavigatortest.screens.base

import java.io.Serializable

/**
 * Base class for defining screen arguments
 */
interface BaseScreen : Serializable {
    val name: String
    val initialValue: String
}