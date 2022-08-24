package com.hrithik.image_edgedetector


import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDelegate
import com.hrithik.image_edgedetector.databinding.LayoutLoadingDialogBinding


class MyApp : Application() {
    private var loadingDialog: Dialog? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        lateinit var instance: MyApp
        fun getContext(): Context = instance.applicationContext
    }


    fun showDialog(context: Context) {
        val binding = LayoutLoadingDialogBinding.inflate(LayoutInflater.from(context))
        if (loadingDialog != null && loadingDialog!!.isShowing) return
        loadingDialog = Dialog(context, R.style.LoaderStyle)
        loadingDialog!!.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(false)
        }
        if (loadingDialog!!.window == null) return
        loadingDialog!!.window!!
            .apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        loadingDialog!!.show()
    }

    fun hideDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }
}