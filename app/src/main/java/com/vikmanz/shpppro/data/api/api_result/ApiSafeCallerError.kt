package com.vikmanz.shpppro.data.api.api_result

import com.vikmanz.shpppro.presentation.utils.UiText

enum class ApiSafeCallerError(val code: Int, val message: String, val uiMessage: UiText) {

    /**
     * GENERAL
     */
    UNKNOWN_EXCEPTION(
        code = 0,
        message = "unknown exception",
        uiMessage = UiText.DynamicString("Невідома помилка")
    ),
    UNKNOWN_HTTP_EXCEPTION(
        code = 1,
        message = "unknown http exception",
        uiMessage = UiText.DynamicString("Невідома помилка серверу")
    );


    companion object {
        private val map = entries.associateBy { it.message }
        infix fun from(value: String?) = map[value]

    }

}

