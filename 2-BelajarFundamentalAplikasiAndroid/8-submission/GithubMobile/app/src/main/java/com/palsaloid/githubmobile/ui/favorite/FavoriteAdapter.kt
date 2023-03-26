package com.palsaloid.githubmobile.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.entity.UsersEntity
import com.palsaloid.githubmobile.databinding.ItemUserFavoriteBinding

class FavoriteAdapter(private val onFavoriteClick: (UsersEntity) -> Unit) : ListAdapter<UsersEntity, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)

        val imgFavorite = holder.binding.imgFavorite
        if (users != null) {
            imgFavorite.setImageDrawable(ContextCompat.getDrawable(imgFavorite.context, R.drawable.ic_favorite_fill))
        } else {
            imgFavorite.setImageDrawable(ContextCompat.getDrawable(imgFavorite.context, R.drawable.ic_favorite_outline))
        }

        imgFavorite.setOnClickListener {
            onFavoriteClick(users)
        }
    }


    class ViewHolder(val binding: ItemUserFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(users: UsersEntity) {
            binding.tvName.text = users.login
            binding.tvUsername.text = "@" + users.login
            Glide.with(itemView.context)
                .load(users.avatarUrl)
                .placeholder(R.drawable.ic_loading)
                .circleCrop()
                .into(binding.imgProfile)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UsersEntity> =
            object : DiffUtil.ItemCallback<UsersEntity>() {
                override fun areItemsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
                    return oldItem.name == newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: UsersEntity,
                    newItem: UsersEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}