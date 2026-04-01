package com.sohaib.paging3.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sohaib.paging3.data.retrofit.entities.Quote
import com.sohaib.paging3.databinding.ItemDataBinding

class QuotePagingAdapter : PagingDataAdapter<Quote, QuotePagingAdapter.QuoteViewHolder>(QuoteDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class QuoteViewHolder(private val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote?) {
            binding.root.text = quote?.content.orEmpty()
        }
    }

    private companion object {
        val QuoteDiffCallback = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean = oldItem == newItem
        }
    }
}
