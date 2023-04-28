package com.palsaloid.dicodingstoryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.dicodingstoryapp.R
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem
import com.palsaloid.dicodingstoryapp.databinding.ItemStoryBinding

class StoryAdapter : PagingDataAdapter<StoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val stories = getItem(position)
        stories?.let { holder.bind(it) }
//
//        holder.itemView.setOnClickListener {
//            val story = StoryItem(
//                stories.id,
//                stories.name,
//                stories.description,
//                stories.photoUrl,
//                stories.createdAt,
//                stories.lat,
//                stories.lon
//            )
//
//
//        }
    }

    inner class ListViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(story: StoryItem) {
                binding.tvNameStory.text = story.name
                binding.tvDescriptionStory.text = story.description

                Glide.with(binding.root)
                    .load(story.photoUrl)
                    .placeholder(R.drawable.ic_replay)
                    .fitCenter()
                    .into(binding.imgStory)
            }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<StoryItem> =
            object : DiffUtil.ItemCallback<StoryItem>() {
                override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                    return oldItem == newItem
                }
            }
    }
}