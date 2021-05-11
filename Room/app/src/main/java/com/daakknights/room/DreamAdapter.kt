package com.daakknights.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DreamListAdapter(onItemClick: OnItemClick) :
    ListAdapter<Dream, DreamListAdapter.DreamViewHolder>(DreamComparator()) {
    private val mOnItemClick: OnItemClick = onItemClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamViewHolder {
        return DreamViewHolder.create(parent, mOnItemClick)
    }

    override fun onBindViewHolder(holder: DreamViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.dream)
    }

    class DreamViewHolder(itemView: View, private val onItemClick: OnItemClick) :
        RecyclerView.ViewHolder(itemView) {
        private val dreamItemView: TextView = itemView.findViewById(R.id.tvDream)
        private val deleteDream: ImageView = itemView.findViewById(R.id.ivDreamDelete)
        fun bind(text: String?) {
            dreamItemView.text = text
            deleteDream.setOnClickListener { onItemClick.onDeleteClick((adapterPosition)) }

        }

        companion object {
            fun create(parent: ViewGroup, onItemClick: OnItemClick): DreamViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.dreams_recycler_view_item, parent, false)
                return DreamViewHolder(view,onItemClick)
            }
        }
    }

    class DreamComparator : DiffUtil.ItemCallback<Dream>() {
        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.dream == newItem.dream
        }
    }

    interface OnItemClick {
        fun onDeleteClick(positionToDelete: Int)
    }
}