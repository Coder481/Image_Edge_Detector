package com.hrithik.image_edgedetector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrithik.image_edgedetector.databinding.LayoutItemImagesDisplayBinding
import com.hrithik.image_edgedetector.viewModel.ViewModel

class ImagesDisplayAdapter(
    private val originalList: List<String>,
    private val edgedList: List<String>,
    private val context: Context,
    private val viewModel: ViewModel
) : RecyclerView.Adapter<ImagesDisplayAdapter.ImagesDisplayViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesDisplayViewHolder {
        val b = LayoutItemImagesDisplayBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImagesDisplayViewHolder(b)
    }

    override fun onBindViewHolder(holder: ImagesDisplayViewHolder, position: Int) {
        val org = originalList[position]
        val edged = edgedList[position]
        holder.bind(org, edged, viewModel, context)
    }

    override fun getItemCount(): Int = originalList.size

    class ImagesDisplayViewHolder(private val b: LayoutItemImagesDisplayBinding)
        : RecyclerView.ViewHolder(b.root){
        fun bind(org: String, edged: String, viewModel: ViewModel, context: Context) {
            viewModel.load(org, context, b.ivInput)
            viewModel.load(edged, context, b.ivOutput)
        }

    }
}