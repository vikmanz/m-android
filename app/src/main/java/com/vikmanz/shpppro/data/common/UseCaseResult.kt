package com.vikmanz.shpppro.data.common

sealed class UseCaseResult<T> {
    class Success<T>(val data: T) : UseCaseResult<T>()
    class Error<T>(val message: String) : UseCaseResult<T>()
    class Loading<T>(val data: T? = null) : UseCaseResult<T>()
}