package com.ovais.objectives.data.user

import com.sarah.objectives.data.user.Data

data class UserResponse(
    val `data`: Data,
    val error: String,
    val success: Boolean
)