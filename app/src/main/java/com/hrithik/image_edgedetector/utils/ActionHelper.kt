package com.hrithik.image_edgedetector.utils

import android.graphics.Bitmap
import android.widget.Toast
import com.hrithik.image_edgedetector.MyApp
import java.io.ByteArrayOutputStream

object ActionHelper {

    fun showToast(msg : String) = Toast.makeText(MyApp.getContext(),msg, Toast.LENGTH_SHORT).show()

    fun Bitmap.toByteArray() : ByteArray{
        val baos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

}