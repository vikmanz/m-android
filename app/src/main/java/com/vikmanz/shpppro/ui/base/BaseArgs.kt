package com.example.fragmentsnavigatortest.screens.base

import java.io.Serializable

/**
 * Base class for defining screen arguments
 */
interface BaseArgs  : Serializable {
    val name: String
}