package com.msieff.movies.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.msieff.movies.domain.model.Content

class ContentDiffUtilCallback : DiffUtil.ItemCallback<Content>() {
    override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem.posterFullPath == newItem.posterFullPath &&
                oldItem.title == newItem.title
    }
}