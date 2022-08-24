package com.hrithik.image_edgedetector.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException

object FileHelper {

    fun getImageFile(context: Context): File? {

        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"

        val downloadFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val storageFile : File = downloadFolder?.absoluteFile!!

        var file: File? = null
        try {
            file = File.createTempFile(
                imageFileName, ".jpg", storageFile
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file
    }
}