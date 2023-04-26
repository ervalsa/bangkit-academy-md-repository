package com.palsaloid.storydicoding.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.storydicoding.R
import com.palsaloid.storydicoding.databinding.ItemStoryBinding
import com.palsaloid.storydicoding.domain.model.Story

class ListStoryAdapter : ListAdapter<Story, ListStoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val stories = getItem(position)
        holder.bind(stories)

        holder.itemView.setOnClickListener {
            val data = Story(
                stories.id,
                stories.name,
                stories.description,
                stories.photoUrl,
                stories.createdAt,
                stories.lat,
                stories.lon
            )

            val toDetailFragment = HomeFragmentDirections.actionNavigationHomeToNavigationDetail()
            toDetailFragment.id = data.id
            holder.itemView.findNavController().navigate(toDetailFragment)
        }
    }

    inner class ListViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
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
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Story> =
            object : DiffUtil.ItemCallback<Story>() {
                override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                    return oldItem == newItem
                }
            }
    }
}