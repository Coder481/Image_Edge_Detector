package com.hrithik.image_edgedetector

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.hrithik.image_edgedetector.databinding.ActivityMainBinding
import com.hrithik.image_edgedetector.databinding.DialogSelectImgSrcBinding
import com.hrithik.image_edgedetector.helper.CannyHelper
import com.hrithik.image_edgedetector.utils.ActionHelper.showToast
import com.hrithik.image_edgedetector.utils.BitmapCompressor.compress
import com.hrithik.image_edgedetector.utils.FileHelper.getImageFile
import com.hrithik.image_edgedetector.viewModel.ViewModel

class MainActivity : AppCompatActivity() {

    private var originalBitmap: Bitmap? = null
    private lateinit var b: ActivityMainBinding
    private lateinit var launcherForCamera: ActivityResultLauncher<Uri>
    private lateinit var launcherForGallery: ActivityResultLauncher<Intent>
    private lateinit var cameraSourceUri: Uri
    private val viewModel : ViewModel by lazy{
        ViewModelProvider(this)[ViewModel::class.java]
    }
    private val myApp : MyApp by lazy{
        applicationContext as MyApp
    }
    companion object{private const val COMPRESS_IMAGE_SIZE = 500}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        registerLaunchers()

        // Hide views for images
        b.inputLayout.visibility = GONE
        b.outputLayout.visibility = GONE


        b.btnSelectImage.setOnClickListener { showImagePickerDialog() }
        b.btnGenerateImage.setOnClickListener {
            originalBitmap?.let { filterImage(it) } ?: showToast("Please choose an image")
        }
        b.btnDisplayImages.setOnClickListener {
            startActivity(Intent(this, ImagesActivity::class.java))
        }

    }

    private fun registerLaunchers() {
        launcherForGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            run {
                if (result.data != null) {
                    val srcUri = result.data!!.data
                    val stream = contentResolver.openInputStream(srcUri!!)
                    originalBitmap = BitmapFactory.decodeStream(stream).compress(COMPRESS_IMAGE_SIZE)
                    b.ivInput.setImageBitmap(originalBitmap)
                    b.inputLayout.visibility = VISIBLE
                }
            }
        }


        launcherForCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){
                result ->
            run {
                if (result){
                    val stream = contentResolver.openInputStream(cameraSourceUri)
                    originalBitmap = BitmapFactory.decodeStream(stream).compress(COMPRESS_IMAGE_SIZE)
                    b.ivInput.setImageBitmap(originalBitmap)
                    b.inputLayout.visibility = VISIBLE
                }
            }
        }
    }

    private fun showImagePickerDialog() {

        val dialogBinding = DialogSelectImgSrcBinding.inflate(layoutInflater)

        fun hideCustomLink(){
            dialogBinding.apply {
                layCustomLink.visibility = GONE
                layLocalStrg.visibility = VISIBLE
            }
        }
        fun hideLocalStorage(){
            dialogBinding.apply {
                layCustomLink.visibility = VISIBLE
                layLocalStrg.visibility = GONE
            }
        }

        // Handle dialog
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialog.show()

        hideCustomLink()
        dialogBinding.apply {
            rbPhoneStorage.apply {
                setOnCheckedChangeListener { _, b -> if(b)hideCustomLink() }
                isChecked = true
            }
            rbLink.setOnCheckedChangeListener { _, b -> if(b)hideLocalStorage() }
            selectFromCamera.setOnClickListener {
                dialog.dismiss()
                launchCameraLauncher() }
            selectFromGallery.setOnClickListener {
                dialog.dismiss()
                launchGalleryLauncher() }
            btnSendLink.setOnClickListener {
                val link = tieLink.text.toString()
                if(link.isEmpty()) {
                    tieLink.error = "Please enter link"
                    return@setOnClickListener
                }

                dialog.dismiss()
                viewModel.apply{
                    processCustomLink(link, this@MainActivity)
                    customBitmap.observe(this@MainActivity){
                        it?.let{
                            originalBitmap = it.compress(COMPRESS_IMAGE_SIZE)
                            b.ivInput.setImageBitmap(originalBitmap)
                            b.inputLayout.visibility = VISIBLE
                        }
                    }
                }
            }
        }

    }


    private fun filterImage(srcImage: Bitmap){
        myApp.showDialog(this)

        val edgedImage = CannyHelper().process(srcImage)
        if(edgedImage == null) {
            myApp.hideDialog()
            showToast("Cannot process image. Please retry!")
            return
        }

        viewModel.uploadImages(srcImage, edgedImage.compress(COMPRESS_IMAGE_SIZE))
        viewModel.uploadSuccessful
            .observe(this){
                it?.let{
                    if(it){
                        b.ivOutput.setImageBitmap(edgedImage)
                        b.outputLayout.visibility = VISIBLE
                        myApp.hideDialog()
                    }
                }
            }
    }

    private fun launchCameraLauncher(){
        val srcFile = getImageFile(this)
        val srcUri = FileProvider.getUriForFile(
            this,
            myApp.packageName + ".provider",
            srcFile!!
        )
        cameraSourceUri = srcUri
        launcherForCamera.launch(srcUri)
    }

    private fun launchGalleryLauncher(){
        val intent = Intent()
        intent.apply {
            type = "image/*"
            action = Intent.ACTION_PICK
        }
        launcherForGallery.launch(intent)
    }

}