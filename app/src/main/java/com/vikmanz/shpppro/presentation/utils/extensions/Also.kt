package com.vikmanz.shpppro.presentation.utils.extensions

import com.vikmanz.shpppro.utils.extensions.log

infix fun <T> T.alsoLog(message: String): T = this.also { log(message) }

infix fun <T> T.alsoLogItAs(title: String): T = this.also { log("$title: ${this.toString()}") }