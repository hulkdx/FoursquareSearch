package com.example.foursquaresearch.features.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foursquaresearch.databinding.ItemSearchBinding
import com.example.foursquaresearch.features.search.model.SearchResult

object SearchAdapterDiff : DiffUtil.ItemCallback<SearchResult>() {
    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem == newItem
    }
}

class SearchAdapter : ListAdapter<SearchResult, SearchAdapter.SearchViewHolder>(SearchAdapterDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class SearchViewHolder(
        private val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: SearchResult) {
            binding.nameTextView.text = item.name
            binding.distanceTextView.text = item.distance
            binding.addressTextView.text = item.address
        }
    }
}
