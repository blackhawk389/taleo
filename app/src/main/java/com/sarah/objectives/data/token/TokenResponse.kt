package com.sarah.objectives.data.token

data class TokenResponse(
    val `data`: Data,
    val error: String,
    val message: String
)