package ua.digitalminds.fortrainerapp.data.result

import com.vikmanz.shpppro.common.UiText

// https://www.baeldung.com/kotlin/enum-find-by-value (Enum class map)
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
//
//    @Suppress("unused")
//    NO_INTERNET_CONNECTION(
//        code = 2,
//        message = "No internet connection",
//        uiMessage = UiText.DynamicString("Немає з'єднання з мережою")
//    ),
//
//
//    /**
//     * REGISTRATION
//     */
//    USER_ALREADY_EXISTS(
//        code = 3,
//        message = "User with this email or username already exists",
//        uiMessage = UiText.DynamicString("Ця електронна пошта вже зареєстрована")
//    ),
//
//
//    /**
//     * AUTHORIZATION
//     */
//    USER_NOT_ACTIVATED(
//        code = 4,
//        message = "User is not activated",
//        uiMessage = UiText.DynamicString("E-mail не активовано")
//    ),
//
//    WRONG_PASSWORD(
//        code = 5,
//        message = "Wrong password",
//        uiMessage = UiText.DynamicString("Неправильний пароль")
//    ),
//
//    USER_NOT_FOUND(
//        code = 6,
//        message = "User not found",
//        uiMessage = UiText.DynamicString("E-mail не знайдено. Спробуйте зареєструватися")
//    ),
//
//
//
//    /**
//     * SECRET CODE
//     */
//    TOKEN_NOT_EXISTS(
//        code = 7,
//        message = "User token not found",
//        uiMessage = UiText.DynamicString("Неправильний код")
//    ),
//
//    TOKEN_EXPIRED(
//        code = 8,
//        message = "Token expired, reactivate token",
//        uiMessage = UiText.DynamicString("Термін дії коду сплинув")
//    ),
//
//
//
//    /**
//     * RESTORE PASSWORD
//     */
//    TOKEN_INVALID(
//    code = 9,
//    message = "Invalid token",
//    uiMessage = UiText.DynamicString("Неправильний код")
//    );



    companion object {
        private val map = ApiSafeCallerError.values().associateBy { it.message }
        infix fun from(value: String?) = map[value]
    // fun getError(value: String?): ApiSafeCallerError? = map[value]
    }

}

