package com.sohaib.roomdatabase.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohaib.roomdatabase.data.dataSource.model.Note
import com.sohaib.roomdatabase.databinding.ListItemNoteBinding
import com.sohaib.roomdatabase.interfaces.OnItemClick

class AdapterHome(private val onItemClick: OnItemClick) : ListAdapter<Note, AdapterHome.CustomViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemNoteBinding.inflate(layoutInflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, onItemClick)
    }

    class CustomViewHolder(private val binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note, onItemClick: OnItemClick) {
            binding.tvTextListItemNote.text = note.text
            binding.btnEditListItemNote.setOnClickListener { onItemClick.onItemClickListener(note) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }
}