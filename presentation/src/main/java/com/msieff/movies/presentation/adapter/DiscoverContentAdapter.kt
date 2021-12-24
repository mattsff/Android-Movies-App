package com.msieff.movies.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msieff.movies.domain.model.Content
import com.msieff.movies.domain.model.ContentCategory
import com.msieff.movies.presentation.R
import localizedName

class DiscoverContentAdapter(context: Context, val onClickOnContent: (Content) -> Unit) :
    ListAdapter<ContentCategory, RecyclerView.ViewHolder>(DiscoverContentDiffUtilCallback()) {

    private val context: Context = context
    private val viewPool = RecyclerView.RecycledViewPool()

    private inner class SectionViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.sectionTitle)

        fun bind(position: Int) {
            val item = currentList[position]
            title.text = item.category.localizedName(itemView.context)

            val layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            layoutManager.initialPrefetchItemCount = 8

            val titledSectionRecycler = itemView.findViewById<RecyclerView>(R.id.sectionRecycler)
            titledSectionRecycler?.run {
                this.setRecycledViewPool(viewPool)
                this.layoutManager = layoutManager
                this.adapter = ContentAdapter(itemView.context, item.contents, onClickOnContent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SectionViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_discover_section, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SectionViewHolder).bind(position)
    }
}

class DiscoverContentDiffUtilCallback : DiffUtil.ItemCallback<ContentCategory>() {
    override fun areItemsTheSame(oldItem: ContentCategory, newItem: ContentCategory): Boolean {
        return oldItem.category == newItem.category
    }

    override fun areContentsTheSame(oldItem: ContentCategory, newItem: ContentCategory): Boolean {
        return oldItem.contents == newItem.contents
    }

}