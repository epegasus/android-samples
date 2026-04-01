package com.sohaib.paging3.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohaib.paging3.databinding.ItemLoadStateBinding

class QuotesLoadStateAdapter(private val onRetry: () -> Unit) : LoadStateAdapter<QuotesLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.errorGroup.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                binding.errorText.text = loadState.error.localizedMessage ?: loadState.error.message
            }
            binding.retryButton.setOnClickListener { onRetry() }
        }
    }
}