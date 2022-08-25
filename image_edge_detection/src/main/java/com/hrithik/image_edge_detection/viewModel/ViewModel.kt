package com.hrithik.image_edge_detection.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.hrithik.image_edge_detection.R
import com.hrithik.image_edge_detection.utils.ActionHelper.showToast
import com.hrithik.image_edge_detection.utils.ActionHelper.toByteArray
import kotlinx.coroutines.launch
import java.util.*


class ViewModel : ViewModel(){

    private val auth : FirebaseAuth by lazy { Firebase.auth }
    private val firebaseStorage : FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    var uploadSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    var customBitmap: MutableLiveData<Bitmap> = MutableLiveData()

    var edgedImagesList: MutableLiveData<List<String>> = MutableLiveData()
    var orgImagesList: MutableLiveData<List<String>> = MutableLiveData()


    /**
     * Methods to process input and edged images,
     * load custom links for input image
     */
    fun uploadImages(original : Bitmap, edged : Bitmap){
        if(auth.currentUser == null)
            signInUser(original, edged)
        else
            viewModelScope.launch { upload(auth.uid, original, edged) }
    }

    private fun upload(id : String?, original : Bitmap, edged : Bitmap) {
        val originalBytes = original.toByteArray()
        val edgedBytes = edged.toByteArray()

        val ts = System.currentTimeMillis()
        val oriImgRef = firebaseStorage.reference.child(id ?: "anonymous")
            .child("original_$ts").putBytes(originalBytes)
        val edgedImgRef = firebaseStorage.reference.child(id ?: "anonymous")
            .child("edged_$ts").putBytes(edgedBytes)

        oriImgRef
            .addOnSuccessListener {
                edgedImgRef
                    .addOnSuccessListener {
                        uploadSuccessful.postValue(true)
                    }
                    .addOnFailureListener{
                        uploadSuccessful.postValue(false)
                        showToast(it.localizedMessage ?: "Unknown Error") }
            }
            .addOnFailureListener{
                uploadSuccessful.postValue(false)
                showToast(it.localizedMessage ?: "Unknown Error") }

    }

    private fun signInUser(original : Bitmap, edged : Bitmap) {
        viewModelScope.launch {
            auth.signInAnonymously()
                .addOnSuccessListener {
                    upload(auth.uid, original, edged)
                }
                .addOnFailureListener {
                    uploadSuccessful.postValue(false)
                    showToast(it.localizedMessage ?: "Unknown Exception") }
        }
    }

    fun processCustomLink(link: String, context : Context){
        viewModelScope.launch {
            Glide.with(context)
                .asBitmap()
                .load(link)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        customBitmap.postValue(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                })
        }
    }


    // Method that load images from URLs into imageViews
    fun load(url : String, context: Context, view : ImageView){
        viewModelScope.launch {
            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_access_time_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(view)
        }
    }


    /**
     * Fetching processed images from database
     * and filtering them to display on screen
     */
    fun fetchImagesList(){
        viewModelScope.launch {
            if(auth.currentUser == null) {
                orgImagesList.postValue(emptyList())
                return@launch
            }

            val listRef = firebaseStorage.reference.child("${auth.uid}")
            listRef.listAll()
                .addOnSuccessListener {
                    if(it.items.isEmpty()){
                        orgImagesList.postValue(emptyList())
                        return@addOnSuccessListener
                    }

                    val map = hashMapOf<String, String>()
                    it.items.forEach { ref ->
                        ref.downloadUrl.addOnSuccessListener { uri ->
                            map[ref.name] = uri.toString()
                            if(map.size == it.items.size){
                                filterImagesList(map)
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    orgImagesList.postValue(emptyList())
                    Log.e("-----Fetch Error-----",it.toString())
                }
        }
    }

    private fun filterImagesList(map: HashMap<String, String>) {

        val edgedList = mutableListOf<String>()
        val orgList = mutableListOf<String>()

        val sortedMap : MutableMap<String, String> = TreeMap(map)
        sortedMap.forEach { (key, value) ->
            if(key.startsWith("edged")) edgedList.add(value)
            else orgList.add(value)
        }

        edgedList.reverse()
        orgList.reverse()
        edgedImagesList.postValue(edgedList)
        orgImagesList.postValue(orgList)
    }

}