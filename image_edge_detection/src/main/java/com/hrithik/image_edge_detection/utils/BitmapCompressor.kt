package com.hrithik.image_edge_detection.utils

import android.graphics.Bitmap

object BitmapCompressor {
    fun Bitmap.compress(maxSize: Int): Bitmap {
        var width = this.width
        var height = this.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }

        return Bitmap.createScaledBitmap(this, width, height, true)
    }
}