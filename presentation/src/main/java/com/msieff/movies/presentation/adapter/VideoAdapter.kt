package com.msieff.movies.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msieff.movies.domain.model.Video
import com.msieff.movies.presentation.R
import load

class VideoAdapter(
    context: Context,
    private val onClickOnVideo: (Video) -> Unit,
) :
    ListAdapter<Video, RecyclerView.ViewHolder>(VideoDiffUtilCallback()) {

    private val context: Context = context

    private inner class VideoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.videoTitle)
        var image: ImageView = itemView.findViewById(R.id.videoImage)

        fun bind(position: Int) {
            val item = currentList[position]
            title.text = item.name
            image.load(item.trailerImagePath)
            itemView.setOnClickListener {
                onClickOnVideo.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoViewHolder).bind(position)
    }
}

class VideoDiffUtilCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}