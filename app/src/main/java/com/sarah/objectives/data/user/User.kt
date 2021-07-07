package com.sarah.objectives.data.user

import java.io.File
import java.io.Serializable


data class User(

    var fullName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phone: String? = null,
    var address: String? = null,
    var dateOfBirth: String? = null,
    var profile: File? = null
):Serializable