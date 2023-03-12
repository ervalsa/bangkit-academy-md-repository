package com.palsaloid.githubmobile.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.ui.profile.FollowAdapter

class HomeAdapter(private val listUser: List<UserResponse>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.tvName.text = user.name
        holder.tvUsername.text = user.login

        Glide.with(holder.itemView)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.imgProfile)
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        val imgProfile: ImageView = itemView.findViewById(R.id.img_profile)
    }
}