package com.hrithik.image_edgedetector.helper

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.lang.Math.atan
import java.lang.Math.sqrt

class CannyHelper {
    fun process(image: Bitmap): Bitmap? {
        var Imagem = image
        val width = Imagem.width
        val height = Imagem.height
        val n = Bitmap.createBitmap(Imagem, 0, 0, width, height)
        val allPixR = Array(width) { IntArray(height) }
        val allPixG = Array(width) { IntArray(height) }
        val allPixB = Array(width) { IntArray(height) }
        for (i in 0 until Imagem.width) {
            for (j in 0 until Imagem.height) {
                val pixi = Imagem.getPixel(i, j)
                allPixR[i][j] = Color.red(pixi)
                allPixG[i][j] = Color.green(pixi)
                allPixB[i][j] = Color.blue(pixi)
            }
        }
        for (i in 2 until width - 2) {
            for (j in 2 until height - 2) {
                val red =
                    ((((allPixR[i - 2][j - 2]) + ((allPixR[i - 1][j - 2]) * 4) + ((allPixR[i][j - 2]) * 7) + ((allPixR[i + 1][j - 2]) * 4) + (allPixR[i + 2][j - 2])
                            + ((allPixR[i - 2][j - 1]) * 4) + ((allPixR[i - 1][j - 1]) * 16) + ((allPixR[i][j - 1]) * 26) + ((allPixR[i + 1][j - 1]) * 16) + ((allPixR[i + 2][j - 1]) * 4
                            ) + ((allPixR[i - 2][j]) * 7) + ((allPixR[i - 1][j]) * 26) + ((allPixR[i][j]) * 41) + ((allPixR[i + 1][j]) * 26) + ((allPixR[i + 2][j]) * 7
                            ) + ((allPixR[i - 2][j + 1]) * 4) + ((allPixR[i - 1][j + 1]) * 16) + ((allPixR[i][j + 1]) * 26) + ((allPixR[i + 1][j + 1]) * 16) + ((allPixR[i + 2][j + 1]) * 4
                            ) + (allPixR[i - 2][j + 2]) + ((allPixR[i - 1][j + 2]) * 4) + ((allPixR[i][j + 2]) * 7) + ((allPixR[i + 1][j + 2]) * 4) + (allPixR[i + 2][j + 2]))) / 273)
                val green =
                    ((((allPixG[i - 2][j - 2]) + ((allPixG[i - 1][j - 2]) * 4) + ((allPixG[i][j - 2]) * 7) + ((allPixG[i + 1][j - 2]) * 4) + (allPixG[i + 2][j - 2])
                            + ((allPixG[i - 2][j - 1]) * 4) + ((allPixG[i - 1][j - 1]) * 16) + ((allPixG[i][j - 1]) * 26) + ((allPixG[i + 1][j - 1]) * 16) + ((allPixG[i + 2][j - 1]) * 4
                            ) + ((allPixG[i - 2][j]) * 7) + ((allPixG[i - 1][j]) * 26) + ((allPixG[i][j]) * 41) + ((allPixG[i + 1][j]) * 26) + ((allPixG[i + 2][j]) * 7
                            ) + ((allPixG[i - 2][j + 1]) * 4) + ((allPixG[i - 1][j + 1]) * 16) + ((allPixG[i][j + 1]) * 26) + ((allPixG[i + 1][j + 1]) * 16) + ((allPixG[i + 2][j + 1]) * 4
                            ) + (allPixG[i - 2][j + 2]) + ((allPixG[i - 1][j + 2]) * 4) + ((allPixG[i][j + 2]) * 7) + ((allPixG[i + 1][j + 2]) * 4) + (allPixG[i + 2][j + 2]))) / 273)
                val blue =
                    (((allPixB[i - 2][j - 2] + (allPixB[i - 1][j - 2] * 4) + ((allPixB[i][j - 2]) * 7) + ((allPixB[i + 1][j - 2]) * 4) + (allPixB[i + 2][j - 2])
                            + (allPixB[i - 2][j - 1] * 4) + ((allPixB[i - 1][j - 1]) * 16) + ((allPixB[i][j - 1]) * 26) + ((allPixB[i + 1][j - 1]) * 16) + ((allPixB[i + 2][j - 1]) * 4
                            ) + (allPixB[i - 2][j] * 7) + ((allPixB[i - 1][j]) * 26) + ((allPixB[i][j]) * 41) + ((allPixB[i + 1][j]) * 26) + ((allPixB[i + 2][j]) * 7
                            ) + ((allPixB[i - 2][j + 1]) * 4) + ((allPixB[i - 1][j + 1]) * 16) + ((allPixB[i][j + 1]) * 26) + ((allPixB[i + 1][j + 1]) * 16) + ((allPixB[i + 2][j + 1]) * 4
                            ) + (allPixB[i - 2][j + 2]) + ((allPixB[i - 1][j + 2]) * 4) + ((allPixB[i][j + 2]) * 7) + ((allPixB[i + 1][j + 2]) * 4) + (allPixB[i + 2][j + 2]))) / 273)
                n.setPixel(i, j, Color.rgb(red, green, blue))
            }
        }
        val allPixRn = Array(width) { IntArray(height) }
        val allPixGn = Array(width) { IntArray(height) }
        val allPixBn = Array(width) { IntArray(height) }
        for (i in 0 until width) {
            for (j in 0 until height) {
                val pixi2 = n.getPixel(i, j)
                allPixRn[i][j] = Color.red(pixi2)
                allPixGn[i][j] = Color.green(pixi2)
                allPixBn[i][j] = Color.blue(pixi2)
            }
        }
        val gx = arrayOf(intArrayOf(-1, 0, 1), intArrayOf(-2, 0, 2), intArrayOf(-1, 0, 1))
        val gy = arrayOf(intArrayOf(1, 2, 1), intArrayOf(0, 0, 0), intArrayOf(-1, -2, -1))
        var new_rx: Int
        var new_ry: Int
        var new_gx : Int
        var new_gy : Int
        var new_bx : Int
        var new_by : Int
        var rc: Int
        var gc: Int
        var bc: Int
        var gradR: Int
        var gradG: Int
        var gradB: Int
        val graidientR = Array(width) { IntArray(height) }
        val graidientG = Array(width) { IntArray(height) }
        val graidientB = Array(width) { IntArray(height) }
        var atanR: Int
        var atanG: Int
        var atanB: Int
        val tanR = Array(width) { IntArray(height) }
        val tanG = Array(width) { IntArray(height) }
        val tanB = Array(width) { IntArray(height) }
        for (i in 1 until Imagem.width - 1) {
            for (j in 1 until Imagem.height - 1) {
                new_rx = 0
                new_ry = 0
                new_gx = 0
                new_gy = 0
                new_bx = 0
                new_by = 0
                rc = 0
                gc = 0
                bc = 0
                for (wi in -1..1) {
                    for (hw in -1..1) {
                        rc = allPixRn[i + hw][j + wi]
                        new_rx += gx[wi + 1][hw + 1] * rc
                        new_ry += gy[wi + 1][hw + 1] * rc
                        gc = allPixGn[i + hw][j + wi]
                        new_gx += gx[wi + 1][hw + 1] * gc
                        new_gy += gy[wi + 1][hw + 1] * gc
                        bc = allPixBn[i + hw][j + wi]
                        new_bx += gx[wi + 1][hw + 1] * bc
                        new_by += gy[wi + 1][hw + 1] * bc
                    }
                }
                gradR = sqrt(((new_rx * new_rx) + (new_ry * new_ry)).toDouble()).toInt()
                graidientR[i][j] = gradR
                gradG = sqrt(((new_gx * new_gx) + (new_gy * new_gy)).toDouble()).toInt()
                graidientG[i][j] = gradG
                gradB = sqrt(((new_bx * new_gx) + (new_by * new_by)).toDouble()).toInt()
                graidientB[i][j] = gradB
                atanR = ((atan(new_ry.toDouble() / new_rx)) * (180 / Math.PI)).toInt()
                when {
                    atanR > 0 && atanR < 22.5 || atanR > 157.5 && atanR < 180 -> atanR = 0
                    atanR > 22.5 && atanR < 67.5 -> atanR = 45
                    atanR > 67.5 && atanR < 112.5 -> atanR = 90
                    atanR > 112.5 && atanR < 157.5 -> atanR = 135
                }
                when (atanR) {
                    0 -> tanR[i][j] = 0
                    45 -> tanR[i][j] = 1
                    90 -> tanR[i][j] = 2
                    135 -> tanR[i][j] = 3
                }
                atanG = ((atan(new_gy.toDouble() / new_gx)) * (180 / Math.PI)).toInt()
                when {
                    atanG > 0 && atanG < 22.5 || atanG > 157.5 && atanG < 180 -> atanG = 0
                    atanG > 22.5 && atanG < 67.5 -> atanG = 45
                    atanG > 67.5 && atanG < 112.5 -> atanG = 90
                    atanG > 112.5 && atanG < 157.5 -> atanG = 135
                }
                when (atanG) {
                    0 -> tanG[i][j] = 0
                    45 -> tanG[i][j] = 1
                    90 -> tanG[i][j] = 2
                    135 -> tanG[i][j] = 3
                }
                atanB = ((atan(new_by.toDouble() / new_bx)) * (180 / Math.PI)).toInt()
                when {
                    atanB > 0 && atanB < 22.5 || atanB > 157.5 && atanB < 180 -> atanB = 0
                    atanB > 22.5 && atanB < 67.5 -> atanB = 45
                    atanB > 67.5 && atanB < 112.5 -> atanB = 90
                    atanB > 112.5 && atanB < 157.5 -> atanB = 135
                }
                when (atanB) {
                    0 -> tanB[i][j] = 0
                    45 -> tanB[i][j] = 1
                    90 -> tanB[i][j] = 2
                    135 -> tanB[i][j] = 3
                }
            }
        }
        val allPixRs = Array(width) { IntArray(height) }
        val allPixGs = Array(width) { IntArray(height) }
        val allPixBs = Array(width) { IntArray(height) }
        for (i in 2 until width - 2) {
            for (j in 2 until height - 2) {
                when {
                    tanR[i][j] == 0 -> {
                        if (graidientR[i - 1][j] < graidientR[i][j] &&
                            graidientR[i + 1][j] < graidientR[i][j])
                                allPixRs[i][j] = graidientR[i][j]
                        else allPixRs[i][j] = 0
                    }
                }
                if (tanR[i][j] == 1) {
                    if (graidientR[i - 1][j + 1] < graidientR[i][j] &&
                        graidientR[i + 1][j - 1] < graidientR[i][j])
                            allPixRs[i][j] = graidientR[i][j]
                    else allPixRs[i][j] = 0
                }
                if (tanR[i][j] == 2) {
                    if (graidientR[i][j - 1] < graidientR[i][j] && graidientR[i][j + 1] < graidientR[i][j]) {
                        allPixRs[i][j] = graidientR[i][j]
                    } else {
                        allPixRs[i][j] = 0
                    }
                }
                if (tanR[i][j] == 3) {
                    if (graidientR[i - 1][j - 1] < graidientR[i][j] && graidientR[i + 1][j + 1] < graidientR[i][j]) {
                        allPixRs[i][j] = graidientR[i][j]
                    } else {
                        allPixRs[i][j] = 0
                    }
                }
                if (tanG[i][j] == 0) {
                    if (graidientG[i - 1][j] < graidientG[i][j] && graidientG[i+1][j] < graidientG[i][j]
                    ) {
                        allPixGs[i][j] = graidientG[i][j]
                    } else {
                        allPixGs[i][j] = 0
                    }
                }
                if (tanG[i][j] == 1) {
                    if (graidientG[i-1][j+1] < graidientG[i][j] && graidientG[i+1][j-1] < graidientG[i][j]
                    ) {
                        allPixGs[i][j] = graidientG[i][j]
                    } else {
                        allPixGs[i][j] = 0
                    }
                }
                if (tanG[i][j] == 2) {
                    if (graidientG[i][j-1] < graidientG[i][j] && graidientG[i][j+1] < graidientG[i][j]
                    ) {
                        allPixGs[i][j] = graidientG[i][j]
                    } else {
                        allPixGs[i][j] = 0
                    }
                }
                if (tanG[i][j] == 3) {
                    if (graidientG[i-1][j-1] < graidientG[i][j] && graidientG[i+1][j+1] < graidientG[i][j]
                    ) {
                        allPixGs[i][j] = graidientG[i][j]
                    } else {
                        allPixGs[i][j] = 0
                    }
                }
                if (tanB[i][j] == 0) {
                    if (graidientB[i-1][j] < graidientB[i][j] && graidientB[i+1][j] < graidientB[i][j]
                    ) {
                        allPixBs[i][j] = graidientB[i][j]
                    } else {
                        allPixBs[i][j] = 0
                    }
                }
                if (tanB[i][j] == 1) {
                    if (graidientB[i-1][j+1] < graidientB[i][j] && graidientB[i+1][j-1] < graidientB[i][j]
                    ) {
                        allPixBs[i][j] = graidientB[i][j]
                    } else {
                        allPixBs[i][j] = 0
                    }
                }
                if (tanB[i][j] == 2) {
                    if (graidientB[i][j-1] < graidientB[i][j] && graidientB[i][j+1] < graidientB[i][j]
                    ) {
                        allPixBs[i][j] = graidientB[i][j]
                    } else {
                        allPixBs[i][j] = 0
                    }
                }
                if (tanB[i][j] == 3) {
                    if (graidientB[i-1][j-1] < graidientB[i][j] && graidientB[i+1][j+1] < graidientB[i][j]
                    ) {
                        allPixBs[i][j] = graidientB[i][j]
                    } else {
                        allPixBs[i][j] = 0
                    }
                }
            }
        }
        val threshold = 50
        val allPixRf = Array(width) { IntArray(height) }
        val allPixGf = Array(width) { IntArray(height) }
        val allPixBf = Array(width) { IntArray(height) }
        val bb = Bitmap.createBitmap(Imagem, 0, 0, width, height)
        for (i in 2 until width - 2) {
            for (j in 2 until height - 2) {
                if (allPixRs[i][j] > threshold) {
                    allPixRf[i][j] = 1
                } else {
                    allPixRf[i][j] = 0
                }
                if (allPixGs[i][j] > threshold) {
                    allPixGf[i][j] = 1
                } else {
                    allPixGf[i][j] = 0
                }
                if (allPixBs[i][j] > threshold) {
                    allPixBf[i][j] = 1
                } else {
                    allPixBf[i][j] = 0
                }
                if ((allPixRf[i][j] == 1) || (allPixGf[i][j] == 1) || (allPixBf[i][j] == 1)
                ) {
                    bb.setPixel(i, j, Color.rgb(255, 255, 255))
                } else bb.setPixel(i, j, Color.rgb(0, 0, 0))
            }
        }
//        saveImage(bb, "fotazo")
        Imagem = Bitmap.createBitmap(bb, 0, 0, bb.width, bb.height)
        return Imagem
    }

    private fun saveImage(finalBitmap: Bitmap, image_name: String) {
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File(root)
        myDir.mkdirs()
        val fname = "Image-$image_name.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        Log.i("LOAD", root + fname)
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}