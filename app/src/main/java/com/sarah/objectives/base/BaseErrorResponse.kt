package com.sarah.objectives.base

data class BaseErrorResponse(
    val data: Any,
    val success: Boolean,
    val error: String
)