package com.sarah.objectives.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    @JvmStatic
    fun getFormattedDateTime(epoch: Long, format: String): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(epoch)
    }

}