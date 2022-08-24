package com.hrithik.image_edge_detection

import android.os.Bundle
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrithik.image_edge_detection.adapter.ImagesDisplayAdapter
import com.hrithik.image_edge_detection.databinding.ActivityImagesDisplayBinding
import com.hrithik.image_edge_detection.viewModel.ViewModel

class DisplayImagesActivity : AppCompatActivity(){

    private lateinit var b : ActivityImagesDisplayBinding
    private val myApp : MyApp by lazy{
        applicationContext as MyApp
    }
    private val viewModel : ViewModel by lazy{
        ViewModelProvider(this)[ViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityImagesDisplayBinding.inflate(layoutInflater)
        setContentView(b.root)

        observeList()
    }

    private fun observeList() {
        myApp.showDialog(this)
        viewModel.fetchImagesList()
        viewModel.orgImagesList.observe(this){
            it?.let{
                myApp.hideDialog()
                if(it.isEmpty()){
                    b.tvNoData.visibility = VISIBLE
                    return@let
                }
                setUpAdapter(
                    it as MutableList<String>,
                    viewModel.edgedImagesList.value!! as MutableList<String>
                )
            }
        }
    }

    private fun setUpAdapter(orgList: MutableList<String>, edgedList: MutableList<String>) {
        val adapter = ImagesDisplayAdapter(orgList,edgedList,this,viewModel)
        b.rvImages.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(this@DisplayImagesActivity)
        }
    }

}