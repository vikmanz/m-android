package com.vikmanz.shpppro.data.dto

data class ErrorServerResponse(
    val status: String,
    val code: Int,
    val message: String,
)