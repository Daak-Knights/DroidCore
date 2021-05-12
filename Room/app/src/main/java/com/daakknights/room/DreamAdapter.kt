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
        holder.bind(current)
    }

    class DreamViewHolder(itemView: View, private val onItemClick: OnItemClick) :
        RecyclerView.ViewHolder(itemView) {
        private val dreamTV: TextView = itemView.findViewById(R.id.tvDream)
        private val dreamET: TextView = itemView.findViewById(R.id.etDream)
        private val deleteDreamIV: ImageView = itemView.findViewById(R.id.ivDreamDelete)
        private val editDreamIV: ImageView = itemView.findViewById(R.id.ivDreamEdit)
        private val doneDreamIV: ImageView = itemView.findViewById(R.id.ivDreamDone)
        fun bind(dream: Dream) {
            dreamTV.text = dream.dream
            dreamET.text = dream.dream
            deleteDreamIV.setOnClickListener { onItemClick.onDelete(dream) }
            editDreamIV.setOnClickListener {
                onEditClick(false)
            }
            doneDreamIV.setOnClickListener {
                onEditClick(true)
                dream.dream = dreamET.editableText.toString()
                onItemClick.onUpdate(dream)
            }

        }

        private fun onEditClick(isDreamEdited: Boolean) {
            dreamET.visibility = if (isDreamEdited) View.GONE else View.VISIBLE
            dreamTV.visibility = if (isDreamEdited) View.VISIBLE else View.GONE
            editDreamIV.visibility = if (isDreamEdited) View.VISIBLE else View.GONE
            deleteDreamIV.visibility = if (isDreamEdited) View.VISIBLE else View.GONE
            doneDreamIV.visibility = if (isDreamEdited) View.GONE else View.VISIBLE
            dreamTV.text = dreamET.text
        }


        companion object {
            fun create(parent: ViewGroup, onItemClick: OnItemClick): DreamViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.dreams_recycler_view_item, parent, false)
                return DreamViewHolder(view, onItemClick)
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
        fun onDelete(dream: Dream)
        fun onUpdate(dream: Dream)
    }
}