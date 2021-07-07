package com.sarah.objectives.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import java.io.File

object ObjectivesHelper {

    fun ContentResolver.getFileName(uri: Uri):String{
        var name = ""
        val returnCursor = this.query(uri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name.toLowerCase().replace(" ","_")
    }


    fun getPermissionArrayAsString() = arrayListOf("Location Permission","Storage Permission","Camera Permission")

    fun getContentType(file: File): String {
        val fileType = getFileType(file)
        return if (fileType == "pdf") {
            "application/pdf"
        } else {
            "image"
        }
    }

    private fun getFileType(file: File): String? = MimeTypeMap.getFileExtensionFromUrl(file.toString())
}