package com.palsaloid.githubmobile.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.remote.response.UserResponse

class FollowAdapter(private val listFollowing: List<UserResponse>) : RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listFollowing[position]
        holder.tvName.text = user.login
        holder.tvUsername.text = "@" + user.login

        Glide.with(holder.itemView)
            .load(user.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_loading)
            .into(holder.imgProfile)
    }

    override fun getItemCount(): Int = listFollowing.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        val imgProfile: ImageView = itemView.findViewById(R.id.img_profile)
    }
}