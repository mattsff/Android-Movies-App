package com.msieff.movies.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msieff.movies.domain.model.Content
import com.msieff.movies.presentation.R
import com.msieff.movies.presentation.adapter.diff.ContentDiffUtilCallback
import load

class SearchAdapter(
    context: Context,
    private var list: List<Content>,
    private val onClickOnContent: (Content) -> Unit,
) :
    ListAdapter<Content, RecyclerView.ViewHolder>(ContentDiffUtilCallback()) {

    private val context: Context = context

    private inner class ContentViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.contentTitle)
        var image: ImageView = itemView.findViewById(R.id.contentImage)

        fun bind(position: Int) {
            val item = list[position]
            title.text = item.title
            image.load(item.posterFullPath)
            itemView.setOnClickListener {
                onClickOnContent.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun submitList(list: List<Content>?) {
        list?.let {
            this.list = it
        }
        super.submitList(list)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContentViewHolder).bind(position)
    }

}

