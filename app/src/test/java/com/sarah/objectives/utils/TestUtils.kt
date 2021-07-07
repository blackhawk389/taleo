package com.sarah.objectives.utils

import com.sarah.objectives.data.blogs.PostItems
import com.sarah.objectives.data.user.User
import com.sarah.objectives.requestbody.TokenRequestBody
import java.io.File

object TestUtils {

    val blog = PostItems(
        1,
        "Test Blog Title",
        "Test Sub Title",
        "Description",
        "https://www.google.com.pk",
        "12/12/12",
        "12/12/12"
    )

    val project = com.sarah.objectives.data.projects.Data(
        1, "Test Blog Title",
        "Test Sub Title",
        "Description",
        "12/12/12",
        "12/12/12"
    )

    val user = User(
        "Syed Ovais",
        "ovais.hussain@venturedive.com",
        "Ovais123",
        "03362402603",
        "Precinct 10-A Bahria Town Karachi",
        "01-July-1997",
        File("Ovais.png")
    )

    val tokenRequestBody = TokenRequestBody(user.email!!, user.password!!)
}

